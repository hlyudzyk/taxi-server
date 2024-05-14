package com.taxiapp.main.net.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterCarsRequest {

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Production year must not be null")
    @Min(value = 1996,message = "Year has to be after 1995")
    @Min(value = 2024,message = "Year has to be before 2024")
    private String productionYear;
}
