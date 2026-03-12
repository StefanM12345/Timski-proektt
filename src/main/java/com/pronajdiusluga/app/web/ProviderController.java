package com.pronajdiusluga.app.web;

import com.pronajdiusluga.app.model.Category;
import com.pronajdiusluga.app.model.ServiceProvider;
import com.pronajdiusluga.app.model.ServiceProviderStatus;
import com.pronajdiusluga.app.model.User;
import com.pronajdiusluga.app.model.city;
import com.pronajdiusluga.app.repository.CategoryRepository;
import com.pronajdiusluga.app.repository.CityRepository;
import com.pronajdiusluga.app.repository.UserRepository;
import com.pronajdiusluga.app.service.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/provider")
@RequiredArgsConstructor
public class ProviderController {

    private final ServiceProviderService serviceProviderService;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String providerDashboard(@AuthenticationPrincipal UserDetails userDetails,
                                    Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();
        var providers = serviceProviderService.findAllByUser(user);
        model.addAttribute("providers", providers);
        return "provider-dashboard";
    }

    @GetMapping("/create")
    public String createForm(@AuthenticationPrincipal UserDetails userDetails,
                             Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();
        model.addAttribute("cities", cityRepository.findAllByOrderByNameAsc());
        model.addAttribute("categories", categoryRepository.findAll());
        return "provider-create";
    }

    @PostMapping("/create")
    public String create(@AuthenticationPrincipal UserDetails userDetails,
                         @RequestParam String name,
                         @RequestParam(required = false) String description,
                         @RequestParam String phone,
                         @RequestParam String address,
                         @RequestParam Long cityId,
                         @RequestParam Long categoryId) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();
        city city = cityRepository.findById(cityId).orElseThrow();
        Category category = categoryRepository.findById(categoryId).orElseThrow();

        ServiceProvider provider = ServiceProvider.builder()
                .name(name)
                .description(description)
                .phone(phone)
                .address(address)
                .city(city)
                .category(category)
                .user(user)
                .status(ServiceProviderStatus.PENDING)
                .build();
        serviceProviderService.save(provider);
        return "redirect:/provider";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();
        ServiceProvider provider = serviceProviderService.getById(id);
        if (!provider.getUser().getId().equals(user.getId())) {
            return "redirect:/provider";
        }
        model.addAttribute("provider", provider);
        model.addAttribute("categories", categoryRepository.findAll());
        return "provider-edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @AuthenticationPrincipal UserDetails userDetails,
                       @RequestParam String name,
                       @RequestParam(required = false) String description,
                       @RequestParam String phone,
                       @RequestParam String address,
                       @RequestParam Long categoryId) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();
        ServiceProvider provider = serviceProviderService.getById(id);
        if (!provider.getUser().getId().equals(user.getId())) {
            return "redirect:/provider";
        }
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        provider.setName(name);
        provider.setDescription(description);
        provider.setPhone(phone);
        provider.setAddress(address);
        provider.setCategory(category);
        serviceProviderService.save(provider);
        return "redirect:/provider";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();
        ServiceProvider provider = serviceProviderService.getById(id);
        if (provider != null && provider.getUser().getId().equals(user.getId())) {
            serviceProviderService.delete(provider);
        }
        return "redirect:/provider";
    }
}

