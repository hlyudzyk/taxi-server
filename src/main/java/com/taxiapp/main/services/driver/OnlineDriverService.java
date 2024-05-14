package com.taxiapp.main.services.driver;

import com.taxiapp.main.persistance.entities.car.Car;
import com.taxiapp.main.persistance.repositories.CarsRepository;
import com.taxiapp.main.processing.onlinedriver.Driver;
import com.taxiapp.main.processing.onlinedriver.DriverStatus;
import com.taxiapp.main.processing.onlinedriver.OnlineDriversRegistry;
import com.taxiapp.main.security.user.User;
import com.taxiapp.main.services.exceptions.DriverToCarMatchException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OnlineDriverService {
    private final CarsRepository carsRepository;
    private final OnlineDriversRegistry onlineDriversRegistry;


    public List<Driver> getAllOnlineDrivers(){
        return onlineDriversRegistry.getAllOnlineDrivers();
    }

    public List<Driver> getAvailableDrivers(){
        return onlineDriversRegistry.getOnlineDriversByStatus(DriverStatus.AVAILABLE);
    }

    public Driver changeDriverStatus(User driver, DriverStatus driverStatus){

        if(!onlineDriversRegistry.isDriverOnline(driver.getId())){
            setDriverOnline(driver);
        }

        Driver onlineDriver = onlineDriversRegistry.getDriver(driver.getId());
        onlineDriver.setDriverStatus(driverStatus);

        return onlineDriver;
    }

    private Driver setDriverOnline(User driver){
        Car driversCar = carsRepository.findByDriver(driver).orElseThrow(
            ()->new DriverToCarMatchException(driver.getId()
        ));

        Driver onlineDriver = new Driver(driver);
        onlineDriver.setCar(driversCar);
        onlineDriversRegistry.addDriver(onlineDriver);

        return onlineDriver;
    }

    public Driver getOnlineDriverById(UUID id){
        return onlineDriversRegistry.getDriver(id);
    }



}
