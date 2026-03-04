package com.pronajdiusluga.app.repository;

import com.pronajdiusluga.app.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    // Пребарување по град + категорија (по имиња)
    List<ServiceProvider> findByCity_NameIgnoreCaseAndCategory_NameIgnoreCase(String city, String category);

    // Пребарување по град
    List<ServiceProvider> findByCity_NameIgnoreCase(String city);

    // Пребарување по категорија
    List<ServiceProvider> findByCategory_NameIgnoreCase(String category);

    // Пребарување по keyword во име (contains)
    List<ServiceProvider> findByNameContainingIgnoreCase(String keyword);
}
