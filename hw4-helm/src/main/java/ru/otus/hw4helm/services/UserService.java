package ru.otus.hw4helm.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.hw4helm.models.User;
import ru.otus.hw4helm.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Mono<User> findById(long id) {
        return userRepository.findById(id);
    }

    public Mono<Void> deleteById(long id) {
        return userRepository.deleteById(id);
    }

    public Mono<User> createUser(User user) {
        var now = java.time.LocalDateTime.now();
        if (user.getId() > 0) {
            user.setId(user.getId());
        }
        user.setUpdatedAt(now);
        user.setCreatedAt(now);

        return userRepository.save(user);
    }

    public Mono<User> updateUser(long id, final Mono<User> userMono) {
        var now = java.time.LocalDateTime.now();
        return this.userRepository.findById(id)
                .flatMap(existUser -> userMono.map(updatedUser -> {
                    existUser.setId(id);
                    existUser.setFirstName(updatedUser.getFirstName());
                    existUser.setLastName(updatedUser.getLastName());
                    existUser.setCreatedAt(existUser.getCreatedAt());
                    existUser.setUpdatedAt(now);

                    return existUser;
                }))
                .flatMap(this.userRepository::save);
    }

}
