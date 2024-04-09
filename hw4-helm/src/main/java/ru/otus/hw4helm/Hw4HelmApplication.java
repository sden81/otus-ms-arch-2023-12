package ru.otus.hw4helm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class Hw4HelmApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hw4HelmApplication.class, args);
	}

}
