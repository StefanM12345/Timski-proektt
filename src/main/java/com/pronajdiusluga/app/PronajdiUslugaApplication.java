package com.pronajdiusluga.app;

import com.pronajdiusluga.app.model.Role;
import com.pronajdiusluga.app.model.User;
import com.pronajdiusluga.app.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PronajdiUslugaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PronajdiUslugaApplication.class, args);
    }

    @Bean
    CommandLineRunner seedUsers(UserRepository repo, PasswordEncoder encoder) {
        return args -> {

            repo.findByUsername("admin").orElseGet(() ->
                    repo.save(new User("admin", encoder.encode("admin123"), Role.ADMIN)));

            repo.findByUsername("user").orElseGet(() ->
                    repo.save(new User("user", encoder.encode("user123"), Role.USER)));

            repo.findByUsername("provider").orElseGet(() ->
                    repo.save(new User("provider", encoder.encode("provider123"), Role.SERVICE_PROVIDER)));
        };
    }
}
