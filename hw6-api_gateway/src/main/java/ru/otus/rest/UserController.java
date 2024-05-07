package ru.otus.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.models.User;
import ru.otus.rest.dto.UserProfileRequestDto;
import ru.otus.rest.dto.UserProfileResponseDto;
import ru.otus.service.UserService;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile/{login}")
    public Mono<ResponseEntity> getUserProfile(@RequestHeader("X-User-Login") String userLoginFromHeader,
                                               @PathVariable String login) {
        if (!userLoginFromHeader.equals(login)) {
            return Mono.just(new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN));
        }

        return userService.findByLogin(login)
                .switchIfEmpty(Mono.just(User.builder().build()))
                .map(user -> {
                    if (user.getId() < 1) {
                        return new ResponseEntity<>("Wrong user", HttpStatus.BAD_REQUEST);
                    }

                    return ResponseEntity.ok(UserProfileResponseDto.builder()
                            .login(user.getLogin())
                            .bornDate(user.getBornDate())
                            .password(user.getPassword())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .build());
                });
    }

    @PutMapping("/profile/{login}")
    public Mono<ResponseEntity> updateUserProfile(@RequestHeader("X-User-Login") String userLoginFromHeader,
                                                  @PathVariable String login,
                                                  @RequestBody UserProfileRequestDto userProfileDto) {
        if (!userLoginFromHeader.equals(login)) {
            return Mono.just(new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN));
        }

        return userService.findByLogin(login)
                .flatMap(user -> userService.updateUser(user.getId(), userProfileDto)
                        .map(count -> count == 0 ? new ResponseEntity<>("Wrong user", HttpStatus.BAD_REQUEST)
                                : ResponseEntity.ok("Updated")));
    }
}
