package com.taxiapp.main.processing.onlinedriver;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class OnlineDriversRegistry {

    private final Map<UUID, Driver> onlineDrivers;

    public OnlineDriversRegistry() {
        this.onlineDrivers = new ConcurrentHashMap<>();
    }

    public void addDriver(Driver driver) {
        onlineDrivers.put(driver.getDriverAccount().getId(), driver);
    }

    public void removeDriver(Driver driver) {
        onlineDrivers.remove(driver.getDriverAccount().getId());
    }

    public Driver getDriver(UUID driverId){
        return onlineDrivers.get(driverId);
    }

    public List<Driver> getAllOnlineDrivers(){
        return onlineDrivers.values().stream().toList();
    }
    public boolean isDriverOnline(UUID driverId) {
        return onlineDrivers.containsKey(driverId);
    }

    public List<Driver> getOnlineDriversByStatus(DriverStatus driverStatus){
        return onlineDrivers.values()
            .stream()
            .filter(d->d.getDriverStatus().equals(driverStatus))
            .toList();
    }

}
