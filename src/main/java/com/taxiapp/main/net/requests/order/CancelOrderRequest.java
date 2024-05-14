package com.taxiapp.main.net.requests.order;

import com.taxiapp.main.services.validation.ValidUUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelOrderRequest {
    @ValidUUID
    private String orderId;
}
