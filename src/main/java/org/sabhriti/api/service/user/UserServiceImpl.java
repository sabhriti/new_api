package org.sabhriti.api.service.user;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.User;
import org.sabhriti.api.dal.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Flux<User> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Mono<User> addNew(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Mono<User> findOneById(String userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public Mono<Void> deleteById(String userId) {
        return this.userRepository.deleteById(userId);
    }
}
