package org.sabhriti.api.service.user;

import org.sabhriti.api.dal.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getAll();

    Mono<User> addNew(User user);

    Mono<User> findOneById(String userId);

    Mono<Void> deleteById(String userId);
}
