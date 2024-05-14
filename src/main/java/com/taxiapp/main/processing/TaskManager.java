package com.taxiapp.main.processing;

import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.services.driver.OnlineDriverService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskManager {

    private final OrdersProcessingService ordersProcessingService;
    private final OnlineDriverService onlineDriverService;

    @Scheduled(fixedRate = 20000)
    public void processOrders() {
        if(onlineDriverService.getAvailableDrivers().isEmpty()) return;

        List<Order> orders = ordersProcessingService.getPendingOrders();
       // ExecutorService executor = Executors.newFixedThreadPool(10);

        for (Order order : orders) {
            ordersProcessingService.processOrder(order);
        }

        //executor.shutdown();
    }


}
