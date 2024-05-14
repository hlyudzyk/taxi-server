package com.taxiapp.main.net.requests;

import com.taxiapp.main.processing.onlinedriver.DriverStatus;
import com.taxiapp.main.services.validation.ValidEnum;
import com.taxiapp.main.services.validation.ValidUUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeDriverStatusRequest {

    @ValidUUID
    private String driverId;

    @ValidEnum(enumClass = DriverStatus.class, message = "Invalid driver status")
    private DriverStatus driverStatus;
}
