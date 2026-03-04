package com.pronajdiusluga.app.repository;

import com.pronajdiusluga.app.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}