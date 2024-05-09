package ru.otus.rest;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.models.User;
import ru.otus.rest.dto.LoginRequestDto;
import ru.otus.rest.dto.RegisterRequestDto;
import ru.otus.rest.dto.RegisterResponseDto;
import ru.otus.service.AuthService;
import ru.otus.service.UserService;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity> login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto.getLogin(), loginRequestDto.getPassword())
                .map(sessionId ->
                {
                    if (!StringUtils.hasText(sessionId)) {
                        return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
                    }

                    ResponseCookie sessionCookie = ResponseCookie.from("session-id", sessionId)
                            .httpOnly(true)
                            .secure(true)
                            .path("/")
                            .maxAge(600)
                            .build();

                    return ResponseEntity
                            .ok()
                            .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                            .build();
                });
    }

    @GetMapping("/logout")
    public ResponseEntity logout(@CookieValue(name = "session-id", required = false) @Nullable String sessionId) {
        if (!StringUtils.hasText(sessionId)) {
            return new ResponseEntity<>("Empty session-id", HttpStatus.OK);
        }

        ResponseCookie sessionCookie = ResponseCookie.from("session-id", sessionId)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();


        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                .body(authService.logout(sessionId) ? "Success logout" : "Wrong session-id");
    }

    @GetMapping("/auth")
    public Mono<ResponseEntity> auth(@CookieValue(name = "session-id", required = false) @Nullable String sessionId) {
        if (!StringUtils.hasText(sessionId)) {
            return Mono.just(new ResponseEntity<>("Need login", HttpStatus.UNAUTHORIZED));
        }

        var authUserId = authService.getAuthUser(sessionId);
        if (Objects.isNull(authUserId)) {
            return Mono.just(new ResponseEntity<>("Need login", HttpStatus.UNAUTHORIZED));
        }

        return userService.findById(authUserId)
                .switchIfEmpty(Mono.just(User.builder().build()))
                .map(user -> {
                    if (user.getId() < 1) {
                        return new ResponseEntity<>("User already deleted", HttpStatus.UNAUTHORIZED);
                    }

                    return ResponseEntity
                            .ok()
                            .header("X-User-Id", String.valueOf(user.getId()))
                            .header("X-User-Login", user.getLogin())
                            .body("Ok");
                });
    }

    @PostMapping("/register")
    public Mono<RegisterResponseDto> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        return userService.registerUser(registerRequestDto)
                .map(savedUser -> RegisterResponseDto.builder()
                        .id(savedUser.getId())
                        .login(savedUser.getLogin())
                        .password(savedUser.getPassword())
                        .firstName(savedUser.getFirstName())
                        .lastName(savedUser.getLastName())
                        .bornDate(savedUser.getBornDate())
                        .build());
    }
}
