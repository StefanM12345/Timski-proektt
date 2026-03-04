package com.pronajdiusluga.app.config;

import lombok.RequiredArgsConstructor;
import com.pronajdiusluga.app.model.*;
import com.pronajdiusluga.app.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;
    private final ServiceProviderRepository serviceProviderRepository;

    @Override
    public void run(String... args) {
        city skopje = cityRepository.findByNameIgnoreCase("Скопје")
                .orElseGet(() -> cityRepository.save(city.builder().name("Скопје").build()));
        city bitola = cityRepository.findByNameIgnoreCase("Битола")
                .orElseGet(() -> cityRepository.save(city.builder().name("Битола").build()));

        Category mechanic = categoryRepository.findByNameIgnoreCase("Автомеханичар")
                .orElseGet(() -> categoryRepository.save(Category.builder().name("Автомеханичар").build()));
        Category barber = categoryRepository.findByNameIgnoreCase("Фризер")
                .orElseGet(() -> categoryRepository.save(Category.builder().name("Фризер").build()));

        if (serviceProviderRepository.count() == 0) {
            serviceProviderRepository.save(ServiceProvider.builder()
                    .name("Auto Ivan")
                    .description("Сервис и дијагностика")
                    .phone("070-123-456")
                    .address("Бул. Партизански Одреди 10")
                    .city(skopje)
                    .category(mechanic)
                    .build());

            serviceProviderRepository.save(ServiceProvider.builder()
                    .name("Barber Studio Lux")
                    .description("Машко шишање и брада")
                    .phone("075-222-333")
                    .address("Широк Сокак 50")
                    .city(bitola)
                    .category(barber)
                    .build());
        }
    }
}

