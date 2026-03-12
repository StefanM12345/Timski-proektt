package com.pronajdiusluga.app.service;

import lombok.RequiredArgsConstructor;
import com.pronajdiusluga.app.model.Application;
import com.pronajdiusluga.app.model.ServiceProvider;
import com.pronajdiusluga.app.model.User;
import com.pronajdiusluga.app.repository.ApplicationRepository;
import com.pronajdiusluga.app.repository.ServiceProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;
    private final ApplicationRepository applicationRepository;

    public List<ServiceProvider> search(String city, String category, String q) {
        boolean hasCity = city != null && !city.isBlank();
        boolean hasCategory = category != null && !category.isBlank();
        boolean hasQ = q != null && !q.isBlank();

        if (hasCity && hasCategory) {
            return serviceProviderRepository.findByCity_NameIgnoreCaseAndCategory_NameIgnoreCase(city, category);
        }
        if (hasCity) {
            return serviceProviderRepository.findByCity_NameIgnoreCase(city);
        }
        if (hasCategory) {
            return serviceProviderRepository.findByCategory_NameIgnoreCase(category);
        }
        if (hasQ) {
            return serviceProviderRepository.findByNameContainingIgnoreCase(q);
        }
        return serviceProviderRepository.findAll();
    }

    public ServiceProvider getById(Long id) {
        return serviceProviderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceProvider not found: " + id));
    }

    public List<ServiceProvider> findAll() {
        return serviceProviderRepository.findAll();
    }

    public ServiceProvider save(ServiceProvider sp) {
        return serviceProviderRepository.save(sp);
    }

    public ServiceProvider findByUser(User user) {
        return serviceProviderRepository.findByUser(user);
    }

    public void delete(ServiceProvider sp) {
        List<Application> applications = applicationRepository.findByServiceProvider(sp);
        applicationRepository.deleteAll(applications);
        serviceProviderRepository.delete(sp);
    }
}
