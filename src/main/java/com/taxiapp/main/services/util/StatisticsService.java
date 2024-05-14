package com.taxiapp.main.services.util;

import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.persistance.entities.order.OrderStatus;
import com.taxiapp.main.persistance.repositories.OrdersRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticsService {
    private final OrdersRepository ordersRepository;

    public double calculateOrderStatusRatio(){
        List<Order> completedOrders = ordersRepository.findAllByOrderStatus(OrderStatus.COMPLETED);
        return completedOrders.size();
    }

    public Map<LocalDate, Integer> calculateTotalOrdersPerTimePeriod(String before,String after) {

        LocalDateTime dateBefore = LocalDateTime.parse(before,DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime dateAfter = LocalDateTime.parse(after,DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<Order> orders = ordersRepository.findAllByOrderedAtBeforeAndOrderedAtAfter(
            dateBefore,dateAfter
        );

        Map<LocalDate, Integer> totalOrdersPerDay = new HashMap<>();
        for (Order order : orders) {
            LocalDate orderDate = order.getOrderedAt().toLocalDate();
            totalOrdersPerDay.put(orderDate, totalOrdersPerDay.getOrDefault(orderDate, 0) + 1);
        }

        return totalOrdersPerDay;
    }




}
