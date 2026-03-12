package com.pronajdiusluga.app.config;
import lombok.RequiredArgsConstructor;
import com.pronajdiusluga.app.model.Category;
import com.pronajdiusluga.app.model.city;
import com.pronajdiusluga.app.repository.CategoryRepository;
import com.pronajdiusluga.app.repository.CityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;

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
    }
}

