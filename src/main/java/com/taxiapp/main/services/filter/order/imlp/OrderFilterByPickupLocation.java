package com.taxiapp.main.services.filter.order.imlp;

import com.taxiapp.main.net.requests.order.FilterOrdersRequest;
import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.services.filter.order.OrderFilter;
import java.util.List;

public class OrderFilterByPickupLocation extends OrderFilter {

    public OrderFilterByPickupLocation(OrderFilter next) {
        super(next);
    }

    @Override
    public List<Order> filter(List<Order> orders, FilterOrdersRequest filter) {
        String pickupLocation = filter.getPickupLocation();
        if(!isFilterStepRequested(pickupLocation)) return orders;

        return orders.stream().filter(o->o.getPickupLocation().contains(pickupLocation)).toList();
    }
}