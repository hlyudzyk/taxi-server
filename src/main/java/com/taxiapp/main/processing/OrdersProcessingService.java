package com.taxiapp.main.processing;

import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.persistance.entities.order.OrderStatus;
import com.taxiapp.main.persistance.repositories.OrdersRepository;
import com.taxiapp.main.processing.onlinedriver.Driver;
import com.taxiapp.main.processing.onlinedriver.DriverStatus;
import com.taxiapp.main.services.util.OrderDriverMatcher;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrdersProcessingService {
    private final OnlineOrdersRegistry onlineOrdersRegistry;
    private final OrdersRepository ordersRepository;
    private final OrderDriverMatcher orderDriverMatcher;

    public void processOrder(Order order) {
        Driver foundDriver = orderDriverMatcher.findDriver(order);

        if (foundDriver != null) {
            foundDriver.setDriverStatus(DriverStatus.BUSY);
            order.setOrderStatus(OrderStatus.IN_PROGRESS);
            order.setCar(foundDriver.getCar());
            order.setDriver(foundDriver.getDriverAccount());
            ordersRepository.save(order);
            System.out.println("Order " + order.getId() +" is in progress!");

        } else {
            offerOrderToQueue(order);
        }
    }

    public void removeOrderFromOnlineRegistry(Order order){
        onlineOrdersRegistry.removeOrderFromRegistry(order);
    }

    public List<Order> getPendingOrders() {
        return onlineOrdersRegistry.getAllOrdersFromRegistry();
    }

    public void offerOrderToQueue(Order order) {
        onlineOrdersRegistry.offerOrderToRegistry(order);
    }

}
