package org.sabhriti.api.service.user;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.dal.repository.UserRepository;
import org.sabhriti.api.service.exception.AlreadyExistsException;
import org.sabhriti.api.transport.email.UserRegistrationMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRegistrationMailSender userRegistrationMailSender;

    @Override
    public Flux<User> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Mono<Object> save(User user) {
        return this.userRepository
                .findUserByEmail(user.getEmail())
                .zipWith(this.userRepository.findUserByUsername(user.getUsername()))
                .flatMap(zipped -> {
                    if (!Objects.equals(zipped.getT1().getId(), user.getId())) {
                        return this.createError("email");
                    }

                    if (!Objects.equals(zipped.getT2().getId(), user.getId())) {
                        return this.createError("username");
                    }

                    return this.createUser(user);
                })
                .switchIfEmpty(this.createUser(user));
    }

    private Mono<User> createUser(User user) {
        return this.userRepository
                .save(user)
                .flatMap(this.userRegistrationMailSender::sendPasswordCreationEmail);
    }

    private Mono<Object> createError(String field) {
        return Mono.error(new AlreadyExistsException("User with same %s already exists.".formatted(field)));
    }

    @Override
    public Mono<User> findOneById(String userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public Mono<Void> deleteById(String userId) {
        return this.userRepository.deleteById(userId);
    }
}
