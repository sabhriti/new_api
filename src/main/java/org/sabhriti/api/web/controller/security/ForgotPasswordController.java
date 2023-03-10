package org.sabhriti.api.web.controller.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.dal.model.user.UserTokenUsage;
import org.sabhriti.api.service.email.PasswordResetEmailService;
import org.sabhriti.api.service.exception.NotFoundException;
import org.sabhriti.api.service.user.UserService;
import org.sabhriti.api.service.user.token.UserTokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final UserService userService;

    private final UserTokenService userTokenService;

    private final PasswordResetEmailService passwordResetEmailService;

    @GetMapping("/forget-password/email={email}")
    public Mono<User> handlePasswordForget(@PathVariable String email) {
        return this.userService
                .findByEmail(email)
                .flatMap(this::createToken)
                .switchIfEmpty(Mono.error(new NotFoundException("User for provided email not found.")));
    }

    private Mono<User> createToken(User user) {
        return this.userTokenService
                .createFor(user, UserTokenUsage.FORGOT_PASSWORD, LocalDateTime.now().plusDays(1))
                .flatMap(userToken -> {
                    var newPassword = RandomStringUtils.randomAlphanumeric(10);
                    return this.userService
                            .updatePassword(newPassword, user)
                            .flatMap(u -> this.passwordResetEmailService.sendMail(user, userToken, newPassword));
                });
    }
}
