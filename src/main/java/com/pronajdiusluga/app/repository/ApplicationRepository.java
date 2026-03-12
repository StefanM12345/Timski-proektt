package com.pronajdiusluga.app.repository;

import com.pronajdiusluga.app.model.Application;
import com.pronajdiusluga.app.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByServiceProvider(ServiceProvider serviceProvider);
}