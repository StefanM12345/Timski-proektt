package com.pronajdiusluga.app.web;

import com.pronajdiusluga.app.model.*;
import com.pronajdiusluga.app.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;

    @PostMapping("/apply/{id}")
    public String apply(@PathVariable Long id,
                        @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow();

        ServiceProvider provider =
                serviceProviderRepository.findById(id).orElseThrow();

        Application application = new Application();

        application.setUser(user);
        application.setServiceProvider(provider);

        applicationRepository.save(application);

        return "redirect:/";
    }
}