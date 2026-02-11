package com.naveen.easycar.controller;

import com.naveen.easycar.dto.CarRequestDto;
import com.naveen.easycar.dto.CarResponseDto;
import com.naveen.easycar.entity.Car;
import com.naveen.easycar.mapper.CarMapper;
import com.naveen.easycar.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CarResponseDto> addCar(@Valid @RequestBody CarRequestDto carDto){
        Car car = mapper.toEntity(carDto);
        Car savedCar = carService.addCar(car);
        return ResponseEntity.ok(mapper.toDto(savedCar));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDto> updateCar(
            @PathVariable Long id,
            @Valid @RequestBody CarRequestDto dto
    ) {

        Car updated = carService.updateCar(id, dto);

        return ResponseEntity.ok(mapper.toDto(updated));
    }


    @GetMapping()
    public ResponseEntity<List<CarResponseDto>> getCars(){
        List<CarResponseDto> cars = carService.getAllActiveCars().stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> getCarById(@PathVariable Long id){
        CarResponseDto cars = mapper.toDto(carService.getCarById(id));
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/search")
    public ResponseEntity<CarResponseDto> getCarByRegistrationNum(@RequestParam String registrationNumber){
        CarResponseDto cars = mapper.toDto(carService.getCarByRegistration(registrationNumber));
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
