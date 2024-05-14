package com.taxiapp.main.services.util;

import com.taxiapp.main.net.requests.UpdatePricingRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class PricingService {
    private final PricingPolicy pricingPolicy;

    public boolean setPricingPolicy(UpdatePricingRequest updatePricingRequest) {
        pricingPolicy.setTariff(updatePricingRequest.getTariff());
        pricingPolicy.setBasicTaxiTypeMultiplier(updatePricingRequest.getBasicTaxiTypeMultiplier());
        pricingPolicy.setCargoTaxiTypeMultiplier(updatePricingRequest.getCargoTaxiTypeMultiplier());
        pricingPolicy.setPremiumTaxiTypeMultiplier(updatePricingRequest.getPremiumTaxiTypeMultiplier());
        return true;
    }
}
