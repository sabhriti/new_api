package org.sabhriti.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.service.user.token.UserTokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user-token")
@RequiredArgsConstructor
public class UserTokenController {
    private final UserTokenService userTokenService;

    @GetMapping("/validate/token={token}")
    public Mono<Boolean> isTokenValid(@PathVariable String token) {
        return this.userTokenService
                .findByToken(token)
                .map(userToken -> {
                    if (userToken.getExpiresOn().isBefore(LocalDateTime.now())) {
                        return false;
                    }

                    if (null != userToken.getIsUsed()) {
                        return !userToken.getIsUsed();
                    }

                    return true;
                })
                .switchIfEmpty(Mono.just(false));
    }
}
