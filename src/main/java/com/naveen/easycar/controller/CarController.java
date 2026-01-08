package com.naveen.easycar.controller;

import com.naveen.easycar.entity.Car;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/customer")
@RestController
public class CarController {
    @PostMapping("/car")
    public ResponseEntity<String> addCar(@RequestBody Car car){

        return ResponseEntity.ok("Car added successfully");
    }
}
