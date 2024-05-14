package com.taxiapp.main.processing.onlinedriver;

import com.taxiapp.main.persistance.entities.car.Car;
import com.taxiapp.main.security.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Driver {
    private final User driverAccount;

    @Setter
    private Car car;

    @Setter
    private String currentLocation;

    @Setter
    private DriverStatus driverStatus;

    public Driver(User driverAccount) {
        this.driverAccount = driverAccount;
        this.driverStatus = DriverStatus.OFFLINE;
        this.car = null;
        this.currentLocation = null;
    }


}
