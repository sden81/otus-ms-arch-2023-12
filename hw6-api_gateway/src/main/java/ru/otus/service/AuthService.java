package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import ru.otus.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final Map<String, Long> sessionIdUserIdMap = new HashMap<>();

    public Mono<String> login(String login, String password) {
        return userService.findByLoginPassword(login, password)
                .switchIfEmpty(Mono.just(User.builder().build()))
                .map(user -> {
                    if (user.getId() < 1) {
                        return "";
                    }

                    var sessionId = generateSessionId();
                    sessionIdUserIdMap.put(sessionId, user.getId());

                    return sessionId;
                });
    }

    public boolean logout(String sessionId) {
        if (!sessionIdUserIdMap.containsKey(sessionId)) {
            return false;
        }

        sessionIdUserIdMap.remove(sessionId);

        return true;
    }

    public Long getAuthUser(String sessionId) {
        if (!StringUtils.hasText(sessionId)) {
            return null;
        }

        return sessionIdUserIdMap.get(sessionId);
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
