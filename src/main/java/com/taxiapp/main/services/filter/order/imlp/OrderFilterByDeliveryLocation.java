package com.taxiapp.main.services.filter.order.imlp;

import com.taxiapp.main.net.requests.order.FilterOrdersRequest;
import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.services.filter.order.OrderFilter;
import java.util.List;

public class OrderFilterByDeliveryLocation extends OrderFilter {

    public OrderFilterByDeliveryLocation(OrderFilter next) {
        super(next);
    }

    @Override
    public List<Order> filter(List<Order> orders, FilterOrdersRequest filter) {
        String deliveryLocation = filter.getDeliveryLocation();
        if(!isFilterStepRequested(deliveryLocation)) return orders;

        return orders.stream().filter(o->o.getDeliveryLocation().contains(deliveryLocation)).toList();
    }
}