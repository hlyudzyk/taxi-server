    package com.taxiapp.main.services.driver;


    import com.taxiapp.main.net.requests.ChangeDriverStatusRequest;
    import com.taxiapp.main.net.responses.DriverDataResponse;
    import com.taxiapp.main.net.responses.DriverFinancialStatementResponse;
    import com.taxiapp.main.persistance.entities.order.Order;
    import com.taxiapp.main.persistance.repositories.OrdersRepository;
    import com.taxiapp.main.processing.onlinedriver.Driver;
    import com.taxiapp.main.processing.onlinedriver.DriverStatus;
    import com.taxiapp.main.security.user.Role;
    import com.taxiapp.main.security.user.User;
    import com.taxiapp.main.security.user.UserRepository;
    import com.taxiapp.main.services.exceptions.NoContentException;
    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.Set;
    import java.util.UUID;
    import java.util.stream.Collectors;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final UserRepository userRepository;
    private final OnlineDriverService onlineDriverService;
    private final OrdersRepository ordersRepository;
    private final CarService carService;

    public boolean changeDriverStatus(ChangeDriverStatusRequest request) {
        UUID driverId = UUID.fromString(request.getDriverId());
        User driver = userRepository.findById(driverId).orElseThrow();
        DriverStatus driverStatus = request.getDriverStatus();

        driver.setLastSession(LocalDateTime.now());
        userRepository.save(driver);

        Driver onlineDriver = onlineDriverService.changeDriverStatus(driver,driverStatus);

        //TODO replace with logger
        System.out.println("Driver " + driverId + " is now online with status: " + onlineDriver.getDriverStatus());

        return true;
    }

    public List<DriverDataResponse> getAllDrivers(){
        //Retrieve online drivers firstly
        Set<DriverDataResponse> driversDataResponse = onlineDriverService.getAllOnlineDrivers()
            .stream()
            .map(DriverDataResponse::new)
            .collect(Collectors.toSet());

        //Retrieve all drivers
        Set<Driver> allDrivers = userRepository.findAllByRole(Role.DRIVER)
            .stream()
            .map(Driver::new).collect(Collectors.toSet());

        //Find each driver's car
        allDrivers.forEach(
            d->d.setCar(
                carService.findCarByDriverIdOrElseEmpty(d.getDriverAccount().getId()).orElse(null)
            )
        );

        //Merge sets of online drivers and all drivers so there are only distinct objects
        driversDataResponse.addAll(
            allDrivers.stream()
            .map(DriverDataResponse::new)
            .collect(Collectors.toSet())
        );

        return driversDataResponse.stream().toList();
    }

    public Driver getOnlineDriverById(UUID id){
        Driver driver = onlineDriverService.getOnlineDriverById(id);
        if (driver ==null) throw new NoContentException("No matching driver with id "+ id);
        return driver;
    }

    public DriverFinancialStatementResponse generateFinancialStatementForDriver(String id) {
        UUID driverId = UUID.fromString(id);

        List<Order> orderByDriver = ordersRepository.findAllByDriverId(driverId);
        if(orderByDriver.isEmpty()) throw new NoContentException("No orders found by this driver Id.");

        int totalOrders = orderByDriver.size();
        double totalRevenue = calculateTotalRevenueForDriver(orderByDriver);
        double avgRevenuePerOrder = totalRevenue/totalOrders;

        return DriverFinancialStatementResponse.builder()
            .totalOrders(totalOrders)
            .totalRevenue(totalRevenue)
            .avgRevenuePerOrder(avgRevenuePerOrder)
            .build();
    }

    private double calculateTotalRevenueForDriver(List<Order> ordersByDriver) {
        double totalRevenue = 0;

        for (Order order : ordersByDriver) {
            totalRevenue += order.getTotalPrice();
        }

        return totalRevenue;
    }
}
