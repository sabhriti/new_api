package org.sabhriti.api.dal.repository;

import org.sabhriti.api.dal.model.user.UserToken;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserTokenRepository extends ReactiveMongoRepository<UserToken, String> {
    Mono<UserToken> findUserTokenByToken(String token);
    Mono<UserToken> findUserTokenByUserId(String userId);
    Mono<Boolean> existsUserTokenByUserId(String userId);
}
