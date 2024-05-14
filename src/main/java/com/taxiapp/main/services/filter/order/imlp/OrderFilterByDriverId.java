package com.taxiapp.main.services.filter.order.imlp;

import com.taxiapp.main.net.requests.order.FilterOrdersRequest;
import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.services.filter.order.OrderFilter;
import java.util.List;
import java.util.UUID;

public class OrderFilterByDriverId extends OrderFilter {

    public OrderFilterByDriverId(OrderFilter next) {
        super(next);
    }

    @Override
    public List<Order> filter(List<Order> orders, FilterOrdersRequest filter) {
        String driverId = filter.getDriverId();
        if(!isFilterStepRequested(driverId)) return orders;
        orders = orders.stream().filter(o->o.getDriver().getId().equals(UUID.fromString(driverId))).toList();
        return orders;
    }
}
