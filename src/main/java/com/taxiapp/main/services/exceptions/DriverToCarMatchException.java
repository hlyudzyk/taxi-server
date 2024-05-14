package com.taxiapp.main.services.exceptions;

import java.util.UUID;

public class DriverToCarMatchException extends RuntimeException{
    public DriverToCarMatchException(UUID driverId) {
        super("Cannot find car assigned to the driver " + driverId);
    }

    public DriverToCarMatchException(UUID driverId,UUID carId) {
        super("Mismatch between car " + carId + " and driver " + driverId);
    }
}
