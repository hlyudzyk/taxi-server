package com.taxiapp.main.persistance.repositories;

import com.taxiapp.main.persistance.entities.car.Car;
import com.taxiapp.main.security.user.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsRepository extends JpaRepository<Car, UUID> {
    Optional<Car> findByDriver(User driver);
}
