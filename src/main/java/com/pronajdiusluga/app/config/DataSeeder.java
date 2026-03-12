package com.pronajdiusluga.app.config;

import lombok.RequiredArgsConstructor;
import com.pronajdiusluga.app.model.Category;
import com.pronajdiusluga.app.model.city;
import com.pronajdiusluga.app.repository.CategoryRepository;
import com.pronajdiusluga.app.repository.CityRepository;
import com.pronajdiusluga.app.repository.ProviderRequestRepository;
import com.pronajdiusluga.app.repository.ServiceProviderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private static final Map<String, String> LATIN_NA_KIRILICA = Map.ofEntries(
            Map.entry("Skopje", "Скопје"),
            Map.entry("Kumanovo", "Куманово"),
            Map.entry("Bitola", "Битола"),
            Map.entry("Prilep", "Прилеп"),
            Map.entry("Tetovo", "Тетово"),
            Map.entry("Štip", "Штип"),
            Map.entry("Veles", "Велес"),
            Map.entry("Ohrid", "Охрид"),
            Map.entry("Strumica", "Струмица"),
            Map.entry("Gostivar", "Гостивар"),
            Map.entry("Kavadarci", "Кавадарци"),
            Map.entry("Kočani", "Кочани"),
            Map.entry("Kičevo", "Кичево"),
            Map.entry("Gevgelija", "Гевгелија"),
            Map.entry("Struga", "Струга"),
            Map.entry("Radoviš", "Радовиш"),
            Map.entry("Kriva Palanka", "Крива Паланка"),
            Map.entry("Negotino", "Неготино"),
            Map.entry("Debar", "Дебар"),
            Map.entry("Sveti Nikole", "Свети Николе"),
            Map.entry("Probištip", "Пробиштип"),
            Map.entry("Delčevo", "Делчево"),
            Map.entry("Vinica", "Виница"),
            Map.entry("Resen", "Ресен"),
            Map.entry("Berovo", "Берово"),
            Map.entry("Kratovo", "Кратово"),
            Map.entry("Bogdanci", "Богданци"),
            Map.entry("Makedonska Kamenica", "Македонска Каменица"),
            Map.entry("Kruševo", "Крушево"),
            Map.entry("Valandovo", "Валандово"),
            Map.entry("Makedonski Brod", "Македонски Брод"),
            Map.entry("Demir Kapija", "Демир Капија"),
            Map.entry("Pehčevo", "Пехчево"),
            Map.entry("Demir Hisar", "Демир Хисар")
    );

    private static final List<String> GRADOVI_MK = List.of(
            "Скопје", "Куманово", "Битола", "Прилеп", "Тетово", "Штип", "Велес", "Охрид",
            "Струмица", "Гостивар", "Кавадарци", "Кочани", "Кичево", "Гевгелија", "Струга",
            "Радовиш", "Крива Паланка", "Неготино", "Дебар", "Свети Николе", "Пробиштип",
            "Делчево", "Виница", "Ресен", "Берово", "Кратово", "Богданци", "Македонска Каменица",
            "Крушево", "Валандово", "Македонски Брод", "Демир Капија", "Пехчево", "Демир Хисар"
    );

    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final ProviderRequestRepository providerRequestRepository;

    @Override
    public void run(String... args) {
        // Ажурирај ги постоечките градови од латиница на кирилица
        for (city grad : cityRepository.findAll()) {
            String kirilica = LATIN_NA_KIRILICA.get(grad.getName());
            if (kirilica != null && !kirilica.equals(grad.getName())) {
                var gradKirilica = cityRepository.findByNameIgnoreCase(kirilica).orElse(null);
                if (gradKirilica != null) {
                    // Кириличниот веќе постои – префрли ги референците и избриши го латинскиот
                    for (var p : serviceProviderRepository.findByCity(grad)) {
                        p.setCity(gradKirilica);
                        serviceProviderRepository.save(p);
                    }
                    for (var r : providerRequestRepository.findByCity(grad)) {
                        r.setCity(gradKirilica);
                        providerRequestRepository.save(r);
                    }
                    cityRepository.delete(grad);
                } else {
                    grad.setName(kirilica);
                    cityRepository.save(grad);
                }
            }
        }

        // Додај ги градовите што недостасуваат
        for (String ime : GRADOVI_MK) {
            cityRepository.findByNameIgnoreCase(ime)
                    .orElseGet(() -> cityRepository.save(city.builder().name(ime).build()));
        }

        Category mechanic = categoryRepository.findByNameIgnoreCase("Автомеханичар")
                .orElseGet(() -> categoryRepository.save(Category.builder().name("Автомеханичар").build()));
        Category barber = categoryRepository.findByNameIgnoreCase("Фризер")
                .orElseGet(() -> categoryRepository.save(Category.builder().name("Фризер").build()));
    }
}

