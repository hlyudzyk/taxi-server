package com.taxiapp.main.net.requests;

import com.taxiapp.main.services.validation.ValidUUID;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCarRequest {

    @ValidUUID
    private String driverId;

    @Pattern(regexp = "[A-Z]{2}\\d{4}[A-Z]{2}", message = "Car plate must match pattern AA0000AA")
    private String carPlate;

    @NotBlank(message = "Manufacturer must not be blank")
    private String manufacturer;

    @NotBlank(message = "Model must not be blank")
    private String model;

    @NotNull(message = "Production year must not be null")
    @Min(1995)
    @Max(2024)
    private int productionYear;

}
