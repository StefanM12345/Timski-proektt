package com.pronajdiusluga.app.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Отстранува unique constraint на user_id во service_provider за да овозможи повеќе огласи по давач.
 * Се извршува при стартување.
 */
@Component
@Order(Integer.MIN_VALUE) // прво пред DataSeeder
@RequiredArgsConstructor
@Slf4j
public class ServiceProviderConstraintMigration implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
            jdbcTemplate.execute("ALTER TABLE service_provider DROP CONSTRAINT IF EXISTS ukmdml3t139l6kfghvw3ha5sqd0");
            log.info("Service provider user_id unique constraint removed (if existed)");
        } catch (Exception e) {
            log.warn("Could not drop service_provider constraint (may not exist): {}", e.getMessage());
        }
    }
}
