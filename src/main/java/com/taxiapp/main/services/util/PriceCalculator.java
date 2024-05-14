package com.taxiapp.main.services.util;

import com.taxiapp.main.persistance.entities.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceCalculator {
    private final PricingPolicy pricingPolicy;

    public double calculatePrice(Order order){
        //TODO: upgrade price calculation
        double multiplier;

        switch (order.getTaxiType()){
            case BASIC -> multiplier=pricingPolicy.getBasicTaxiTypeMultiplier();
            case CARGO -> multiplier=pricingPolicy.getCargoTaxiTypeMultiplier();
            case PREMIUM -> multiplier=pricingPolicy.getPremiumTaxiTypeMultiplier();
            default -> multiplier = 1;
        }

        return order.getDistance()*pricingPolicy.getTariff()*multiplier;


    }

}
