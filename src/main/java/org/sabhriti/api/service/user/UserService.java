package org.sabhriti.api.service.user;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.dal.repository.UserRepository;
import org.sabhriti.api.exception.AlreadyExistsException;
import org.sabhriti.api.service.email.PasswordCreationEmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordCreationEmailService passwordCreationEmailService;

    private final BCryptPasswordEncoder passwordEncoder;

    public Flux<User> getAll() {
        return this.userRepository.findAll();
    }

    public Mono<Object> save(User user) {

        return this.userRepository
                .existsByEmailOrUsername(user.getEmail(), user.getUsername())
                .flatMap(userAlreadyExists -> {
                    if (userAlreadyExists) {
                        return this.userRepository
                                .existsByEmail(user.getEmail())
                                .flatMap(userWithSameEmailExists -> {
                                    if (userWithSameEmailExists) {
                                        return this.createError("email");
                                    } else {
                                        return this.createError("username");
                                    }
                                });
                    } else {
                        return this.createUser(user);
                    }
                });
    }

    public Mono<User> updatePassword(String password, User user) {
        user.setPassword(this.passwordEncoder.encode(password));
        user.setIsInitialPassword(false);

        return this.userRepository.save(user);
    }

    private Mono<User> createUser(User user) {
        var rawPassword = user.getPassword();
        user.setPassword(this.passwordEncoder.encode(rawPassword));
        return this.userRepository
                .save(user)
                .flatMap(u -> this.passwordCreationEmailService.sendMail(u, rawPassword));
    }

    private Mono<Object> createError(String field) {
        return Mono.error(new AlreadyExistsException("User with same %s already exists.".formatted(field)));
    }

    public Mono<User> findOneById(String userId) {
        return this.userRepository.findById(userId);
    }

    public Mono<User> findByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    public Mono<User> findByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    public Mono<Void> deleteById(String userId) {
        return this.userRepository.deleteById(userId);
    }
}