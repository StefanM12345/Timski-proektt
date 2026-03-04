package com.pronajdiusluga.app.repository;

import com.pronajdiusluga.app.model.city;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<city, Long> {
    Optional<city> findByNameIgnoreCase(String name);
}
