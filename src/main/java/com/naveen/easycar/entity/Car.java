package com.naveen.easycar.entity;

import com.naveen.easycar.enums.CarCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

@Entity
@Table(
        name = "cars",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "registration_number")
        }
)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    private String brand;
    private String model;

    @Enumerated(EnumType.STRING)
    private CarCategory category;

    @Column(name = "daily_rate", nullable = false)
    private BigDecimal dailyRate;

    @Column(name = "is_active")
    private boolean active = true;

    public Car() {
    }
    public Car(Long id, String registrationNumber, String brand, String model, CarCategory category, BigDecimal dailyRate, boolean active) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.dailyRate = dailyRate;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
