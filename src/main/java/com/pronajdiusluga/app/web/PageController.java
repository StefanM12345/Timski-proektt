package com.pronajdiusluga.app.web;

import lombok.RequiredArgsConstructor;
import com.pronajdiusluga.app.repository.CategoryRepository;
import com.pronajdiusluga.app.repository.CityRepository;
import com.pronajdiusluga.app.service.ServiceProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final ServiceProviderService serviceProviderService;
    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, name = "q") String q,
            Model model
    ) {
        model.addAttribute("providers", serviceProviderService.search(city, category, q));
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());

        model.addAttribute("city", city);
        model.addAttribute("category", category);
        model.addAttribute("q", q);

        return "index";
    }}
