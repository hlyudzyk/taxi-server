package com.taxiapp.main.services.filter.order;

import com.taxiapp.main.net.requests.order.FilterOrdersRequest;
import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.services.filter.order.imlp.OrderFilterByDeliveryLocation;
import com.taxiapp.main.services.filter.order.imlp.OrderFilterByDriverId;
import com.taxiapp.main.services.filter.order.imlp.OrderFilterByPickupLocation;
import com.taxiapp.main.services.filter.order.imlp.OrderFilterByStatus;
import com.taxiapp.main.services.filter.order.imlp.OrderFilterByTaxiType;
import com.taxiapp.main.services.filter.order.imlp.OrderFilterByUserId;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrdersFilterChain {
    private OrderFilter chain;

    public OrdersFilterChain() {
        buildChain();
    }

    private void buildChain() {
       chain = new OrderFilterByUserId(
                    new OrderFilterByPickupLocation(
                        new OrderFilterByDeliveryLocation(
                            new OrderFilterByStatus(
                                new OrderFilterByTaxiType(
                                    new OrderFilterByDriverId(
                                    null
                                    )
                                )
                            )
                        )
                    )
                );
    }

    public List<Order> filterOrders(List<Order> orders, FilterOrdersRequest filter) {
        return chain.doFilter(orders, filter);
    }
}