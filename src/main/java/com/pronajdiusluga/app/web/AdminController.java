package com.pronajdiusluga.app.web;

import com.pronajdiusluga.app.model.ProviderRequest;
import com.pronajdiusluga.app.model.ProviderRequestStatus;
import com.pronajdiusluga.app.model.Role;
import com.pronajdiusluga.app.model.ServiceProvider;
import com.pronajdiusluga.app.model.ServiceProviderStatus;
import com.pronajdiusluga.app.model.User;
import com.pronajdiusluga.app.repository.ProviderRequestRepository;
import com.pronajdiusluga.app.repository.UserRepository;
import com.pronajdiusluga.app.service.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProviderRequestRepository providerRequestRepository;
    private final UserRepository userRepository;
    private final ServiceProviderService serviceProviderService;

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("pendingRequests",
                providerRequestRepository.findByStatus(ProviderRequestStatus.PENDING));
        model.addAttribute("providers", serviceProviderService.findAll());
        model.addAttribute("pendingProviders", serviceProviderService.findByStatus(ServiceProviderStatus.PENDING));
        return "admin-dashboard";
    }

    @PostMapping("/provider-requests/{id}/approve")
    public String approve(@PathVariable Long id) {
        ProviderRequest request = providerRequestRepository.findById(id)
                .orElseThrow();

        User user = request.getUser();
        user.setRole(Role.SERVICE_PROVIDER);
        userRepository.save(user);

        request.setStatus(ProviderRequestStatus.APPROVED);
        providerRequestRepository.save(request);

        return "redirect:/admin";
    }

    @PostMapping("/provider-requests/{id}/reject")
    public String reject(@PathVariable Long id) {
        ProviderRequest request = providerRequestRepository.findById(id)
                .orElseThrow();

        request.setStatus(ProviderRequestStatus.REJECTED);
        providerRequestRepository.save(request);

        return "redirect:/admin";
    }

    @PostMapping("/providers/{id}/delete")
    public String deleteProvider(@PathVariable Long id) {
        ServiceProvider provider = serviceProviderService.getById(id);
        serviceProviderService.delete(provider);
        return "redirect:/admin";
    }

    @PostMapping("/providers/{id}/approve-ad")
    public String approveAd(@PathVariable Long id) {
        ServiceProvider provider = serviceProviderService.getById(id);
        provider.setStatus(ServiceProviderStatus.APPROVED);
        serviceProviderService.save(provider);
        return "redirect:/admin";
    }

    @PostMapping("/providers/{id}/reject-ad")
    public String rejectAd(@PathVariable Long id) {
        ServiceProvider provider = serviceProviderService.getById(id);
        provider.setStatus(ServiceProviderStatus.REJECTED);
        serviceProviderService.save(provider);
        return "redirect:/admin";
    }
}
