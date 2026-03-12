package com.pronajdiusluga.app.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterForm {

    @NotBlank(message = "Username е задолжително.")
    @Size(min = 3, max = 50, message = "Username мора да биде од 3 до 50 карактери.")
    private String username;

    @NotBlank(message = "Password е задолжително.")
    @Size(min = 6, max = 100, message = "Password мора да има најмалку 6 карактери.")
    private String password;

    @NotBlank(message = "Потврда на password е задолжителна.")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

