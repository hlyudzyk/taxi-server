package com.taxiapp.main.net.responses;

import com.taxiapp.main.persistance.entities.car.Car;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDataResponse {
    private String carId;
    private String carPlate;
    private String carManufacturer;
    private String carModel;
    private int carProductionYear;
    private String driverId;

    public CarDataResponse(Car car) {
        this.carId = car.getId().toString();
        this.carPlate = car.getCarPlate();
        this.carManufacturer = car.getManufacturer();
        this.carModel = car.getModel();
        this.carProductionYear = car.getProduction_year();
        this.driverId = car.getDriver().getId().toString();
    }
}
