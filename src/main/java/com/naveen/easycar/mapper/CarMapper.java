package com.naveen.easycar.mapper;

import com.naveen.easycar.dto.CarRequestDto;
import com.naveen.easycar.dto.CarResponseDto;
import com.naveen.easycar.entity.Car;
import org.springframework.context.annotation.Bean;


public class CarMapper {
    public Car toEntity(CarRequestDto dto){
        Car car = new Car();
        car.setRegistrationNumber(dto.getRegistrationNumber());
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setCategory(dto.getCategory());
        car.setDailyRate(dto.getDailyRate());
        return car;
    }

    public CarResponseDto toDto(Car car) {
        CarResponseDto dto = new CarResponseDto();
        dto.setId(car.getId());
        dto.setRegistrationNumber(car.getRegistrationNumber());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setCategory(car.getCategory());
        dto.setDailyRate(car.getDailyRate());
        dto.setActive(car.isActive());
        return dto;
    }
}
