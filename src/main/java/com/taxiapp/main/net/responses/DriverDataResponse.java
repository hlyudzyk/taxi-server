package com.taxiapp.main.net.responses;

import com.taxiapp.main.processing.onlinedriver.Driver;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDataResponse {
    private String driverId;
    private String driverName;
    private String driverEmail;
    private String driverStatus;
    private String carPlate;
    private String currentLocation;
    private LocalDate birthday;

    public DriverDataResponse(Driver driver) {
        this.driverId = driver.getDriverAccount().getId().toString();
        this.driverName = driver.getDriverAccount().getFirstname();
        this.driverEmail = driver.getDriverAccount().getEmail();
        this.driverStatus = driver.getDriverStatus().toString();
        this.birthday = driver.getDriverAccount().getBirthday();
        this.currentLocation = Objects.requireNonNullElse(driver.getCurrentLocation(),"Not set");
        this.carPlate = driver.getCar()==null?"Not assigned":driver.getCar().getCarPlate();
    }
}
