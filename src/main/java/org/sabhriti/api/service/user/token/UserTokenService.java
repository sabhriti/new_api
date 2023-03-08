package org.sabhriti.api.service.user.token;

import org.sabhriti.api.dal.model.user.UserToken;
import org.sabhriti.api.dal.model.user.User;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface UserTokenService {
    Mono<UserToken> createFor(User user, String reason, LocalDateTime expiresOn);
}
