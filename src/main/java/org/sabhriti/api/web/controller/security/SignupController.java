package org.sabhriti.api.web.controller.security;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.dal.model.user.UserActivationStatus;
import org.sabhriti.api.dal.model.user.UserRoles;
import org.sabhriti.api.service.security.RandomPasswordGenerator;
import org.sabhriti.api.service.user.UserService;
import org.sabhriti.api.web.dto.SignupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    Mono<ResponseEntity<String>> signUp(@RequestBody SignupRequest signupRequest) {
        var userToStore = new User();
        userToStore.setName(signupRequest.name());
        userToStore.setPassword(RandomPasswordGenerator.generate());
        userToStore.setUsername(signupRequest.username());
        userToStore.setEmail(signupRequest.email());
        userToStore.setRoles(List.of(UserRoles.USER));
        userToStore.setActivationStatus(UserActivationStatus.NEW);

        return this.userService
                .save(userToStore)
                .flatMap(o -> this.createUserCreatedResponse());
    }

    private Mono<ResponseEntity<String>> createUserCreatedResponse() {
        return Mono.just(new ResponseEntity<>("New user created", HttpStatus.OK));
    }
}
