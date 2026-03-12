package com.pronajdiusluga.app.web;

import com.pronajdiusluga.app.model.Role;
import com.pronajdiusluga.app.model.User;
import com.pronajdiusluga.app.repository.UserRepository;
import com.pronajdiusluga.app.web.dto.RegisterForm;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(
            @Valid @ModelAttribute("registerForm") RegisterForm form,
            BindingResult bindingResult
    ) {
        if (form.getPassword() != null && form.getConfirmPassword() != null
                && !form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "Password-ите не се совпаѓаат.");
        }

        if (form.getUsername() != null && userRepository.findByUsername(form.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "username.exists", "Овој username веќе постои.");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        User user = new User(
                form.getUsername().trim(),
                passwordEncoder.encode(form.getPassword()),
                Role.USER
        );
        userRepository.save(user);

        return "redirect:/login?registered";
    }
}
