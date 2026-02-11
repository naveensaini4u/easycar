package com.naveen.easycar.dto;

import com.naveen.easycar.enums.CarCategory;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public class CarRequestDto {

    @NotBlank(message = "Registration number is required")
    @Pattern(
            regexp = "^[A-Z0-9-]{5,15}$",
            message = "Invalid registration number format"
    )
    private String registrationNumber;

    @NotBlank(message = "Brand is required")
    @Size(min = 2, max = 50)
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(min = 1, max = 50)
    private String model;

    @NotNull(message = "Car category is required")
    private CarCategory category;

    @NotNull(message = "Daily rate is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Daily rate must be greater than 0")
    private BigDecimal dailyRate;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarCategory getCategory() {
        return category;
    }

    public void setCategory(CarCategory category) {
        this.category = category;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }
}
