package ru.otus.hw4helm.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw4helm.models.User;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {
}
