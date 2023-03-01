package org.sabhriti.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.User;
import org.sabhriti.api.dal.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Test APIs", description = "Test APIs for demo purpose")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public Flux<User> getAll() {
        return this.userRepository.findAll();
    }

    @DeleteMapping("/{userId}")
    public Mono<Void> deleteByUserId(@PathVariable String userId) {
        return this.userRepository.deleteUserById(userId);

    }
}
