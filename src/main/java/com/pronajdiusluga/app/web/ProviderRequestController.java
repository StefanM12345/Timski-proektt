package com.pronajdiusluga.app.web;

import com.pronajdiusluga.app.model.*;
import com.pronajdiusluga.app.repository.CategoryRepository;
import com.pronajdiusluga.app.repository.CityRepository;
import com.pronajdiusluga.app.repository.ProviderRequestRepository;
import com.pronajdiusluga.app.repository.UserRepository;
import com.pronajdiusluga.app.web.dto.ProviderRequestForm;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/provider-request")
public class ProviderRequestController {

    private final ProviderRequestRepository providerRequestRepository;
    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ProviderRequestController(ProviderRequestRepository providerRequestRepository,
                                     CityRepository cityRepository,
                                     CategoryRepository categoryRepository,
                                     UserRepository userRepository) {
        this.providerRequestRepository = providerRequestRepository;
        this.cityRepository = cityRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        if (!model.containsAttribute("providerRequestForm")) {
            model.addAttribute("providerRequestForm", new ProviderRequestForm());
        }
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "provider-request";
    }

    @PostMapping
    public String submitRequest(
            @Valid @ModelAttribute("providerRequestForm") ProviderRequestForm form,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        Long cityId;
        Long categoryId;
        try {
            cityId = Long.parseLong(form.getCityId());
            categoryId = Long.parseLong(form.getCategoryId());
        } catch (NumberFormatException e) {
            bindingResult.reject("ids.invalid", "Невалидни вредности за град/категорија.");
            cityId = null;
            categoryId = null;
        }

        city city = null;
        Category category = null;

        if (cityId != null) {
            city = cityRepository.findById(cityId).orElse(null);
            if (city == null) {
                bindingResult.rejectValue("cityId", "city.notFound", "Непостоечки град.");
            }
        }

        if (categoryId != null) {
            category = categoryRepository.findById(categoryId).orElse(null);
            if (category == null) {
                bindingResult.rejectValue("categoryId", "category.notFound", "Непостоечка категорија.");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("cities", cityRepository.findAll());
            model.addAttribute("categories", categoryRepository.findAll());
            return "provider-request";
        }

        ProviderRequest request = ProviderRequest.builder()
                .user(user)
                .city(city)
                .category(category)
                .name(form.getName().trim())
                .description(form.getDescription())
                .phone(form.getPhone())
                .address(form.getAddress())
                .status(ProviderRequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        providerRequestRepository.save(request);

        return "redirect:/?providerRequestSubmitted";
    }
}

