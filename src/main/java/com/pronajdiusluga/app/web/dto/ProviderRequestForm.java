package com.pronajdiusluga.app.web.dto;

import jakarta.validation.constraints.NotBlank;

public class ProviderRequestForm {

    @NotBlank(message = "Името на бизнисот е задолжително.")
    private String name;

    private String description;

    @NotBlank(message = "Телефон е задолжителен.")
    private String phone;

    @NotBlank(message = "Адреса е задолжителна.")
    private String address;

    @NotBlank(message = "Град е задолжителен.")
    private String cityId;

    @NotBlank(message = "Категорија е задолжителна.")
    private String categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}

