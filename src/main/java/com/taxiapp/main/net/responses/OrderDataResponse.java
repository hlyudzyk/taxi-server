package com.taxiapp.main.net.responses;

import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.persistance.entities.order.OrderStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDataResponse {
    private String id;
    private String userId;
    private String userName;
    private String driverId;
    private String driverName;
    private String pickupLocation;
    private String deliveryLocation;
    private double price;
    private double distance;
    private String carId;
    private String taxiType;
    private LocalDateTime orderedAt;
    private LocalDateTime finishedAt;
    private OrderStatus orderStatus;

    public OrderDataResponse(Order o) {
        this.id = o.getId().toString();
        this.userId = o.getUser().getId().toString();
        this.userName = o.getUser().getUsername();
        this.price = o.getTotalPrice();
        this.distance = o.getDistance();
        this.taxiType = o.getTaxiType().toString();
        if(!o.getOrderStatus().equals(OrderStatus.PENDING)) {
            this.driverId = o.getDriver().getId().toString();
            this.driverName = o.getDriver().getUsername();
            this.carId = o.getCar().getId().toString();
            this.finishedAt = o.getFinishedAt();
        }
        this.pickupLocation = o.getPickupLocation();
        this.deliveryLocation = o.getDeliveryLocation();
        this.orderedAt = o.getOrderedAt();
        this.orderStatus = o.getOrderStatus();
    }
}

