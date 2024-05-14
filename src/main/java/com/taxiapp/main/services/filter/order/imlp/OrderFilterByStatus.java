package com.taxiapp.main.services.filter.order.imlp;

import com.taxiapp.main.net.requests.order.FilterOrdersRequest;
import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.persistance.entities.order.OrderStatus;
import com.taxiapp.main.services.filter.order.OrderFilter;
import java.util.List;

public class OrderFilterByStatus extends OrderFilter {

    public OrderFilterByStatus(OrderFilter next) {
        super(next);
    }

    @Override
    protected List<Order> filter(List<Order> orders, FilterOrdersRequest filter) {
        OrderStatus orderStatus = filter.getOrderStatus();
        if(!isFilterStepRequested(orderStatus)) return orders;
        return orders.stream().filter(o->o.getOrderStatus().equals(orderStatus)).toList();

    }
}
