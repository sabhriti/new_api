package org.sabhriti.api.web.controller.security;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.service.security.JwtTokenProvider;
import org.sabhriti.api.service.user.UserService;
import org.sabhriti.api.web.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return Mono.just(new ResponseEntity<>(this.jwtTokenProvider.generateToken(user), HttpStatus.OK));
    }

    private Mono<ResponseEntity<String>> createResponseForBadRequest(String message) {
        return Mono.just(new ResponseEntity<>(message, HttpStatus.BAD_REQUEST));
    }
}
