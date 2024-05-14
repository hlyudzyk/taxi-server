package com.taxiapp.main.services.util;

import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.processing.onlinedriver.Driver;
import com.taxiapp.main.services.driver.OnlineDriverService;
import com.taxiapp.main.services.util.positioning.LocationService;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class OrderDriverMatcher {
    private final OnlineDriverService onlineDriverService;
    private final LocationService locationService;

    public Driver findDriver(Order order) {
        List<Driver> drivers = onlineDriverService.getAvailableDrivers();
        double minDistance = Double.MAX_VALUE;

        for(Driver driver: drivers) {
            //TODO uncomment
            //double distance = locationService.calculateDistance(order.getPickupLocation().getLatitude(),order.getPickupLocation().getLongitude(),
              //  driver.getCurrentLocation().getLatitude(),driver.getCurrentLocation().getLongitude());
            double distance = new Random().nextDouble(200);
            if(distance < minDistance) {
                minDistance = distance;
            }
        }

        System.out.println(minDistance);
        //TODO implement logic to find suitable driver
        if(drivers.isEmpty()) return null;
        return drivers.get(0);
    }

    private void findByHaversine(){
        List<Driver> drivers = onlineDriverService.getAllOnlineDrivers();

    }




}
