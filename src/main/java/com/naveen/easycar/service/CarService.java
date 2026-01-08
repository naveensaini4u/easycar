package com.naveen.easycar.service;

import com.naveen.easycar.entity.Car;
import com.naveen.easycar.enums.CarCategory;
import com.naveen.easycar.exception.BookingConflictException;
import com.naveen.easycar.exception.EntityNotFoundException;
import com.naveen.easycar.repository.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car addCar(Car car) {

        carRepository.findByRegistrationNumber(
                car.getRegistrationNumber()
        ).ifPresent(existing -> {
            throw new BookingConflictException(
                    "Car with registration number already exists"
            );
        });

        return carRepository.save(car);
    }

    public List<Car> getAllActiveCars() {
        return carRepository.findByActiveTrue();
    }

    public List<Car> getCarsByCategory(CarCategory category) {
        return carRepository.findByCategory(category)
                .stream()
                .filter(Car::isActive)
                .toList();
    }

    public Car deactivateCar(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Car not found")
                );

        car.setActive(false);
        return carRepository.save(car);
    }
}

