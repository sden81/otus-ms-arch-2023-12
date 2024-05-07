package ru.otus.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class RegisterResponseDto {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate bornDate;
    private String login;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
