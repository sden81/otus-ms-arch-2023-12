package org.example.rest;

import org.example.rest.dto.HealthResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public HealthResponseDto health() {
        return HealthResponseDto.of("ok");
    }
}
