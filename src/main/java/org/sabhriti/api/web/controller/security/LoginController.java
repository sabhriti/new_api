package org.sabhriti.api.web.controller.security;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.dal.model.user.UserActivationStatus;
import org.sabhriti.api.exception.InactiveUserRequestedException;
import org.sabhriti.api.exception.InvalidCredentialsException;
import org.sabhriti.api.service.security.JwtTokenProvider;
import org.sabhriti.api.service.user.UserService;
import org.sabhriti.api.web.dto.security.LoginRequest;
import org.sabhriti.api.web.dto.security.LoginResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Mono<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return this.userService
                .findByUsername(loginRequest.username())
                .flatMap(user -> {
                    if (!this.passwordMatches(loginRequest, user)) {
                        return Mono.error(new InvalidCredentialsException("invalid password"));
                    } else if (!user.getActivationStatus().equals(UserActivationStatus.ACTIVE)) {
                        return Mono.error(new InactiveUserRequestedException("not an active user"));
                    } else {
                        return Mono.just(new LoginResponse(this.jwtTokenProvider.generateToken(user)));
                    }
                })
                .switchIfEmpty(Mono.error(new InvalidCredentialsException("invalid username")));

    }

    private boolean passwordMatches(LoginRequest loginRequest, User user) {
        return this.passwordEncoder.matches(loginRequest.password(), user.getPassword());
    }
}
