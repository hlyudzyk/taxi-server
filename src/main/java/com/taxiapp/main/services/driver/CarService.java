package com.taxiapp.main.services.driver;

import com.taxiapp.main.net.requests.AssignCarRequest;
import com.taxiapp.main.net.requests.NewCarRequest;
import com.taxiapp.main.net.responses.CarDataResponse;
import com.taxiapp.main.persistance.entities.car.Car;
import com.taxiapp.main.persistance.repositories.CarsRepository;
import com.taxiapp.main.security.user.User;
import com.taxiapp.main.security.user.UserRepository;
import com.taxiapp.main.services.exceptions.NonUniqueDriverAssigningException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarsRepository carsRepository;
    private final UserRepository userRepository;

    public List<CarDataResponse> getAllCars(){
        List<Car> cars = carsRepository.findAll();
        return cars.stream().map(CarDataResponse::new).toList();

    }

    public CarDataResponse registerCar(NewCarRequest newCarRequest){
        User driver = userRepository.findById(UUID.fromString(newCarRequest.getDriverId()))
            .orElseThrow(()->new EntityNotFoundException("Cannot find driver with id: " + newCarRequest.getDriverId()));

        if(carsRepository.findByDriver(driver).isPresent())
            throw new NonUniqueDriverAssigningException(
                "Driver " + driver.getId() + " is already assigned to the car"
            );


        Car car = Car.builder()
            .manufacturer(newCarRequest.getManufacturer())
            .model(newCarRequest.getModel())
            .carPlate(newCarRequest.getCarPlate())
            .driver(driver)
            .build();

        return new CarDataResponse(carsRepository.save(car));

    }

    public CarDataResponse assignCarToDriver(AssignCarRequest assignCarRequest) {
        Car car = carsRepository.findById(UUID.fromString(assignCarRequest.getCarId())).orElseThrow(
            ()->new EntityNotFoundException("Cannot find car with id " + assignCarRequest.getCarId())
        );

        User driver = userRepository.findById(UUID.fromString(assignCarRequest.getDriverId())).orElseThrow(
            ()->new EntityNotFoundException("Cannot find driver with id " + assignCarRequest.getDriverId())
        );

        car.setDriver(driver);

        return new CarDataResponse(carsRepository.save(car));

    }

    public CarDataResponse findCarById(UUID id) {
        return new CarDataResponse(carsRepository.findById(id).orElseThrow(
            ()->new EntityNotFoundException("Cannot find car with id " + id)
        ));
    }

    public Car findCarByDriverId(UUID driverId) {
        User driver = userRepository.findById(driverId)
            .orElseThrow(()->new EntityNotFoundException("Cannot find driver with id: " + driverId));

        return carsRepository.findByDriver(driver).orElseThrow(
            ()->new EntityNotFoundException("Cannot find car with driver id " + driverId)
        );
    }

    public Optional<Car> findCarByDriverIdOrElseEmpty(UUID driverId) {
        User driver = userRepository.findById(driverId)
            .orElseThrow(()->new EntityNotFoundException("Cannot find driver with id: " + driverId));

        return carsRepository.findByDriver(driver);
    }
}
