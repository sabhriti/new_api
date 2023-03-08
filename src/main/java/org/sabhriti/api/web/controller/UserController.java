package org.sabhriti.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.service.user.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<User> allUsers() {
        return this.userService.getAll();
    }

    @PostMapping
    public Mono<Object> save(@RequestBody User user) {
        return this.userService.save(user);
    }

    @GetMapping("/id={userId}")
    public Mono<User> findById(@PathVariable String userId) {
        return this.userService.findOneById(userId);
    }

    @GetMapping("/username={username}")
    public Mono<User> findByUsername(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }

    @DeleteMapping("/id={userId}")
    public Mono<Void> delete(@PathVariable String userId) {
        return this.userService.deleteById(userId);
    }
}
