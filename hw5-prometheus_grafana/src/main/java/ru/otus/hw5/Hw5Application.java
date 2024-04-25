package ru.otus.hw5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class Hw5Application {

	public static void main(String[] args) {
		SpringApplication.run(Hw5Application.class, args);
	}

}
