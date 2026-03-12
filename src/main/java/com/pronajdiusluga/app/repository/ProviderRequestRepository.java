package com.pronajdiusluga.app.repository;

import com.pronajdiusluga.app.model.ProviderRequest;
import com.pronajdiusluga.app.model.ProviderRequestStatus;
import com.pronajdiusluga.app.model.city;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderRequestRepository extends JpaRepository<ProviderRequest, Long> {

    List<ProviderRequest> findByStatus(ProviderRequestStatus status);
    List<ProviderRequest> findByCity(city city);
}

