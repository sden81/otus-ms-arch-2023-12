package ru.otus.hw5.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.hw5.models.User;
import ru.otus.hw5.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public Mono<User> getUser(@PathVariable long id) {
        return userService.findById(id);
    }

    @PostMapping()
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public Mono<User> updateUser(@PathVariable long id, @RequestBody Mono<User> user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> updateUser(@PathVariable long id) {
        return userService.deleteById(id);
    }
}
