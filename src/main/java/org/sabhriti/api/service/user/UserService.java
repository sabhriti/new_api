package org.sabhriti.api.service.user;

import org.sabhriti.api.dal.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getAll();

    Mono<Object> save(User user);

    Mono<User> updatePassword(String password, User user);

    Mono<User> findOneById(String userId);

    Mono<User> findByUsername(String username);

    Mono<User> findByEmail(String username);

    Mono<Void> deleteById(String userId);
}
