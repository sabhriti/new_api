package org.sabhriti.api.web.controller.security;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.dal.model.user.UserToken;
import org.sabhriti.api.service.exception.BadPasswordException;
import org.sabhriti.api.service.exception.InvalidTokenException;
import org.sabhriti.api.service.exception.NotFoundException;
import org.sabhriti.api.service.user.UserService;
import org.sabhriti.api.service.user.token.UserTokenService;
import org.sabhriti.api.web.dto.CreatePasswordRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class PasswordCreationController {

    private final UserService userService;

    private final UserTokenService userTokenService;

    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/create-password")
    Mono<User> createPassword(@RequestBody CreatePasswordRequest signupRequest) {
        return this.userTokenService
                .findByToken(signupRequest.token())
                .flatMap(userToken -> {
                    if (!userToken.getIsUsed() && userToken.getExpiresOn().isAfter(LocalDateTime.now())) {
                        return this.checkForUserAndUpdatePassword(signupRequest, userToken);
                    } else {
                        return Mono.error(new InvalidTokenException("The token has been already used."));
                    }
                })
                .switchIfEmpty(this.createNotFoundError("Invalid token provided. Cannot be located in database."));
    }

    private Mono<User> checkForUserAndUpdatePassword(CreatePasswordRequest signupRequest, UserToken userToken) {
        return this.userService
                .findOneById(userToken.getUserId())
                .flatMap(user -> this.doUpdatePassword(signupRequest, user))
                .switchIfEmpty(
                        this.createNotFoundError("User associated with the token not found.")
                );
    }

    private Mono<User> doUpdatePassword(CreatePasswordRequest signupRequest, User user) {
        if (this.passwordEncoder.encode(signupRequest.password()).equals(user.getPassword())) {
            return Mono.error(new BadPasswordException("New password and the old password cannot be same."));
        } else {
            return this.userService.updatePassword(signupRequest.password(), user);
        }
    }

    private Mono<User> createNotFoundError(String message) {
        return Mono.error(new NotFoundException(message));
    }
}
