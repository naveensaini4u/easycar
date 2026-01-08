package com.naveen.easycar.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance_windows")
public class MaintenanceWindow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String reason;

    public MaintenanceWindow(Long id, Car car, LocalDate startDate, LocalDate endDate, String reason) {
        this.id = id;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

