package com.taxiapp.main.net.requests.order;


import com.taxiapp.main.persistance.entities.order.OrderStatus;
import com.taxiapp.main.persistance.entities.order.TaxiType;
import com.taxiapp.main.services.validation.ValidEnum;
import com.taxiapp.main.services.validation.ValidUUID;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterOrdersRequest {

    @ValidUUID
    private String userId;

    @ValidUUID
    private String driverId;

    private LocalDateTime timeAfter;

    private LocalDateTime timeBefore;

    private String pickupLocation;

    private String deliveryLocation;

    @ValidEnum(enumClass = TaxiType.class, message = "Invalid taxi type")
    private TaxiType taxiType;

    @ValidEnum(enumClass = OrderStatus.class, message = "Invalid order status")
    private OrderStatus orderStatus;
}
