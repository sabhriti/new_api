package org.sabhriti.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.dal.model.user.UserActivationStatus;
import org.sabhriti.api.dal.model.user.UserRoles;
import org.sabhriti.api.service.exception.InvalidTokenException;
import org.sabhriti.api.service.exception.NotFoundException;
import org.sabhriti.api.service.security.TokenProvider;
import org.sabhriti.api.service.user.UserService;
import org.sabhriti.api.service.user.token.UserTokenService;
import org.sabhriti.api.web.dto.CreatePasswordRequest;
import org.sabhriti.api.web.dto.LoginRequest;
import org.sabhriti.api.web.dto.SignupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final UserService userService;

    private final UserTokenService userTokenService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    Mono<ResponseEntity<String>> signUp(@RequestBody SignupRequest signupRequest) {
        var userToStore = new User();
        userToStore.setName(signupRequest.name());
        userToStore.setPassword(this.passwordEncoder.encode(signupRequest.password()));
        userToStore.setUsername(signupRequest.username());
        userToStore.setEmail(signupRequest.email());
        userToStore.setRoles(List.of(UserRoles.USER));
        userToStore.setActivationStatus(UserActivationStatus.NEW);

        return this.userService.save(userToStore).flatMap(o -> this.createUserCreatedResponse());
    }

    @PostMapping("/create-password")
    Mono<Object> createPassword(@RequestBody CreatePasswordRequest signupRequest) {
        return this.userTokenService
                .findByToken(signupRequest.token())
                .flatMap(userToken -> {
                    if (!userToken.getIsUsed() && userToken.getExpiresOn().isAfter(LocalDateTime.now())) {
                        return this.userService
                                .findOneById(userToken.getUserId())
                                .flatMap(user -> {
                                    user.setPassword(this.passwordEncoder.encode(signupRequest.password()));
                                    return this.userService.save(user);
                                }).switchIfEmpty(Mono.error(new NotFoundException("'User associated with the token cannot be located in database.")));
                    } else {
                        return Mono.error(new InvalidTokenException("The token has been already used."));
                    }
                }).switchIfEmpty(Mono.error(new NotFoundException("Invalid token provided. Cannot be located in database.")));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest loginRequest) {
        return this.userService
                .findByUsername(loginRequest.username())
                .flatMap(user -> this.handleValidUser(loginRequest, user))
                .switchIfEmpty(this.createResponseForBadRequest("Invalid username"));
    }

    private Mono<ResponseEntity<String>> handleValidUser(LoginRequest loginRequest, User user) {
        if (this.passwordMatches(loginRequest, user)) {
            return this.createTokenResponse(user);
        } else {
            return this.createResponseForBadRequest("Invalid password");
        }
    }

    private boolean passwordMatches(LoginRequest loginRequest, User user) {
        return this.passwordEncoder.matches(loginRequest.password(), user.getPassword());
    }

    private Mono<ResponseEntity<String>> createTokenResponse(User user) {
        return Mono.just(new ResponseEntity<>(this.tokenProvider.generateToken(user), HttpStatus.OK));
    }

    private Mono<ResponseEntity<String>> createUserCreatedResponse() {
        return Mono.just(new ResponseEntity<>("New user created", HttpStatus.OK));
    }

    private Mono<ResponseEntity<String>> createResponseForBadRequest(String message) {
        return Mono.just(new ResponseEntity<>(message, HttpStatus.BAD_REQUEST));
    }
}
