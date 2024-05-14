package com.taxiapp.main.net.requests.order;

import com.taxiapp.main.persistance.entities.order.TaxiType;
import com.taxiapp.main.services.validation.ValidEnum;
import com.taxiapp.main.services.validation.ValidUUID;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderRequest {
    @ValidUUID
    private String userId;

    @NotNull(message = "Pickup location is required")
    private String pickupLocation;

    @NotNull(message = "Delivery location is required")
    private String deliveryLocation;

    @ValidEnum(enumClass = TaxiType.class, message = "Invalid taxi type")
    private TaxiType taxiType;
}
