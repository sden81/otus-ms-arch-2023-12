package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import ru.otus.models.User;
import ru.otus.repo.UserRepository;
import ru.otus.rest.dto.RegisterRequestDto;
import ru.otus.rest.dto.UserProfileRequestDto;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public Mono<User> findByLoginPassword(String login, String password) {
        return userRepository.findByLoginPassword(login, password);
    }

    public Mono<User> registerUser(RegisterRequestDto registerRequestDto) {
        var now = LocalDateTime.now();
        return userRepository.save(User.builder()
                .login(registerRequestDto.getLogin())
                .password(registerRequestDto.getPassword())
                .bornDate(registerRequestDto.getBornDate())
                .firstName(registerRequestDto.getFirstName())
                .lastName(registerRequestDto.getLastName())
                .createdAt(now)
                .updatedAt(now)
                .build());
    }

    public Mono<Integer> updateUser(long userId, UserProfileRequestDto userProfileRequestDto) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.just(User.builder().build()))
                .flatMap(user -> {
                    if (user.getId() < 1) {
                        return Mono.just(0);
                    }

                    if (StringUtils.hasText(userProfileRequestDto.getFirstName())) {
                        user.setFirstName(userProfileRequestDto.getFirstName());
                    }
                    if (StringUtils.hasText(userProfileRequestDto.getLastName())) {
                        user.setLastName(userProfileRequestDto.getLastName());
                    }
                    if (StringUtils.hasText(userProfileRequestDto.getPassword())) {
                        user.setPassword(userProfileRequestDto.getPassword());
                    }
                    if (Objects.nonNull(userProfileRequestDto.getBornDate())) {
                        user.setBornDate(userProfileRequestDto.getBornDate());
                    }
                    user.setUpdatedAt(LocalDateTime.now());

                    return userRepository.save(user)
                            .map(savedUser -> 1);
                });
    }
}
