package com.taxiapp.main.net.requests;

import com.taxiapp.main.services.validation.ValidUUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignCarRequest {
    @ValidUUID
    private String carId;
    @ValidUUID
    private String driverId;
}
