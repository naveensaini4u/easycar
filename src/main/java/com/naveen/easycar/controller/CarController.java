package com.naveen.easycar.controller;

import com.naveen.easycar.dto.CarRequestDto;
import com.naveen.easycar.dto.CarResponseDto;
import com.naveen.easycar.entity.Car;
import com.naveen.easycar.mapper.CarMapper;
import com.naveen.easycar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/car")
@RestController
public class CarController {

    private final CarService carService;
    private final CarMapper mapper;

    public CarController(CarService carService, CarMapper mapper) {
        this.carService = carService;
        this.mapper = mapper;
    }
    @PostMapping()
    public ResponseEntity<CarResponseDto> addCar(@RequestBody CarRequestDto carDto){
        Car car = mapper.toEntity(carDto);
        Car savedCar = carService.addCar(car);
        return ResponseEntity.ok(mapper.toDto(savedCar));
    }

    @GetMapping()
    public ResponseEntity<List<CarResponseDto>> getCars(){
        List<CarResponseDto> cars = carService.getAllActiveCars().stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> getCar(@PathVariable Long id){
        CarResponseDto cars = mapper.toDto(carService.getCarById(id));
        return ResponseEntity.ok(cars);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCar(
            @PathVariable Long id
    ) {
        carService.deactivateCar(id);
        return ResponseEntity.noContent().build();
    }


}
