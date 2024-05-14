package com.taxiapp.main.services.filter.order;

import com.taxiapp.main.net.requests.order.FilterOrdersRequest;
import com.taxiapp.main.persistance.entities.order.Order;
import java.util.List;

public abstract class OrderFilter {
    protected OrderFilter next;

    protected OrderFilter(OrderFilter next) {
        this.next = next;
    }

    protected abstract List<Order> filter(List<Order> orders, FilterOrdersRequest filter);
    protected boolean isFilterStepRequested(Object o){
        return o!=null;
    }
    public List<Order> doFilter(List<Order> orders, FilterOrdersRequest filter){

        orders = filter(orders,filter);

        if (next != null) {
            return next.doFilter(orders, filter);
        } else {
            return orders;
        }
    }
}