package ru.otus.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.otus.models.User;

import java.time.LocalDate;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {
    @Query("SELECT * FROM users WHERE login = $1 LIMIT 1")
    Mono<User> findByLogin(String login);

    @Query("SELECT * FROM users WHERE login = $1 AND password = $2 LIMIT 1")
    Mono<User> findByLoginPassword(String login, String password);
}
