package com.naveen.easycar.repository;

import com.naveen.easycar.entity.Car;
import com.naveen.easycar.enums.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByRegistrationNumber(String registrationNumber);

    List<Car> findByActiveTrue();

    List<Car> findByCategory(CarCategory category);
}

