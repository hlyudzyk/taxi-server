package com.taxiapp.main.net.controllers;

import com.taxiapp.main.net.requests.AssignCarRequest;
import com.taxiapp.main.net.requests.NewCarRequest;
import com.taxiapp.main.net.responses.CarDataResponse;
import com.taxiapp.main.services.driver.CarService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/car")
@AllArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/new")
    public ResponseEntity<CarDataResponse> createCar(@Valid @RequestBody NewCarRequest newCarRequest) {
        return ResponseEntity.ok(carService.registerCar(newCarRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarDataResponse>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @PostMapping("/assign")
    public ResponseEntity<CarDataResponse> assignCar(@Valid @RequestBody AssignCarRequest assignCarRequest) {
        return ResponseEntity.ok(carService.assignCarToDriver(assignCarRequest));
    }


}
