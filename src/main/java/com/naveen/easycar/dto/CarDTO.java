package com.naveen.easycar.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CarDTO {
    @NotEmpty(message = "Name is mandatory")
    String name;

    @NotEmpty(message = "Registration Number is mandatory")
    String registrationNum;

    @NotEmpty(message = "Manufacture can not be empty")
    String manufacture;

    @NotEmpty(message = "Vehicle type can not be empty")
    String type;

    @NotEmpty(message = "Model can not be empty")
    String model;


    @NotNull(message = "Daily Rate is mandatory")
    @Min(value = 100, message = "Daily Rate should be at least 100")
    double dailyRate;

}
