package org.sabhriti.api.dal.repository;

import org.sabhriti.api.dal.model.user.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findUserByUsername(String username);
    Mono<User> findUserByEmail(String email);
    Mono<Boolean> existsByEmailOrUsername(String email, String username);
    Mono<Boolean> existsByEmail(String email);
}
