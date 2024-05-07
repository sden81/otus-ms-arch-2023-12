package ru.otus.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserProfileRequestDto {
    private String firstName;
    private String lastName;
    private LocalDate bornDate;
    private String password;
}
