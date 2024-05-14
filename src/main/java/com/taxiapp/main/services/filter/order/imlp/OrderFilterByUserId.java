package com.taxiapp.main.services.filter.order.imlp;

import com.taxiapp.main.net.requests.order.FilterOrdersRequest;
import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.services.filter.order.OrderFilter;
import java.util.List;
import java.util.UUID;

public class OrderFilterByUserId extends OrderFilter {
    private OrderFilter next;

    public OrderFilterByUserId(OrderFilter next) {
        super(next);
    }

    @Override
    public List<Order> filter(List<Order> orders, FilterOrdersRequest filter) {
        String userId = filter.getUserId();
        if(!isFilterStepRequested(userId)) return orders;

        orders = orders.stream().filter(o->o.getUser().getId().equals(UUID.fromString(userId))).toList();

        return orders;
    }
}