package ru.otus.hw5.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw5.models.User;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {
}
