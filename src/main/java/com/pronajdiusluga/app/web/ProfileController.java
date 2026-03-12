package com.pronajdiusluga.app.web;

import com.pronajdiusluga.app.model.ServiceProvider;
import com.pronajdiusluga.app.model.User;
import com.pronajdiusluga.app.repository.UserRepository;
import com.pronajdiusluga.app.service.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
    private final ServiceProviderService serviceProviderService;

    @GetMapping
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();
        model.addAttribute("user", user);

        ServiceProvider provider = serviceProviderService.findByUser(user);
        model.addAttribute("provider", provider);

        return "profile";
    }
}
