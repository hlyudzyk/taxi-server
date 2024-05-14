package com.taxiapp.main.net.requests;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePricingRequest {
    @Min(1)
    private double tariff;
    @Min(1)
    private double basicTaxiTypeMultiplier;
    @Min(1)
    private double cargoTaxiTypeMultiplier;
    @Min(1)
    private double premiumTaxiTypeMultiplier;
}


