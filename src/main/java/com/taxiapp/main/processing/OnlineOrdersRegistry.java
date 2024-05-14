package com.taxiapp.main.processing;

import com.taxiapp.main.persistance.entities.order.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class OnlineOrdersRegistry {
    private final Map<UUID,Order> ordersRegistry;

    public OnlineOrdersRegistry() {
        this.ordersRegistry = new ConcurrentHashMap<>();
    }

    public void offerOrderToRegistry(Order order) {
         ordersRegistry.put(order.getId(),order);
    }

    public List<Order> getAllOrdersFromRegistry() {
        return new ArrayList<>(ordersRegistry.values());
    }

    public void removeOrderFromRegistry(Order order){
        ordersRegistry.remove(order.getId());
    }

    public int getOrderPoolSize() {
        return ordersRegistry.size();
    }


    public boolean isOrderPoolEmpty() {
        return ordersRegistry.isEmpty();
    }

    public void clearOrderPool() {
        ordersRegistry.clear();
    }
    public Order getOrderById(UUID orderId) {
        for (Order order : ordersRegistry.values()) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

}
