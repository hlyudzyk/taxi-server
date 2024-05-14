package com.taxiapp.main.services.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PricingPolicy {
    private double tariff;
    private double basicTaxiTypeMultiplier;
    private double cargoTaxiTypeMultiplier;
    private double premiumTaxiTypeMultiplier;
    private double companyChargePerOrder;
}
