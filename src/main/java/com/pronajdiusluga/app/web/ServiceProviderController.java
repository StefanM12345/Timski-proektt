package com.pronajdiusluga.app.web;

import lombok.RequiredArgsConstructor;
import com.pronajdiusluga.app.model.ServiceProvider;
import com.pronajdiusluga.app.service.ServiceProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ServiceProviderController {

    private final ServiceProviderService serviceProviderService;

    // Пример:
    // /api/providers/search?city=Скопје&category=Автомеханичар
    // /api/providers/search?city=Скопје
    // /api/providers/search?category=Фризер
    // /api/providers/search?q=auto
    @GetMapping("/search")
    public List<ServiceProvider> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, name = "q") String keyword
    ) {
        return serviceProviderService.search(city, category, keyword);
    }

    @GetMapping("/{id}")
    public ServiceProvider getOne(@PathVariable Long id) {
        return serviceProviderService.getById(id);
    }

    @PostMapping
    public ServiceProvider create(@RequestBody ServiceProvider sp) {
        return serviceProviderService.save(sp);
    }
}
