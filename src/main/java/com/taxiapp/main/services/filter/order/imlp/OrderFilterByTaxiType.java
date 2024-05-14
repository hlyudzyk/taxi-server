package com.taxiapp.main.services.filter.order.imlp;

import com.taxiapp.main.net.requests.order.FilterOrdersRequest;
import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.persistance.entities.order.TaxiType;
import com.taxiapp.main.services.filter.order.OrderFilter;
import java.util.List;

public class OrderFilterByTaxiType extends OrderFilter {

    public OrderFilterByTaxiType(OrderFilter next) {
        super(next);
    }

    @Override
    public List<Order> filter(List<Order> orders, FilterOrdersRequest filter) {
        TaxiType taxiType = filter.getTaxiType();
        if(!isFilterStepRequested(taxiType)) return orders;
        return orders.stream().filter(o->o.getTaxiType().equals(taxiType)).toList();

    }
}
