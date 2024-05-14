package com.taxiapp.main.net.controllers;

import com.taxiapp.main.net.requests.UpdatePricingRequest;
import com.taxiapp.main.services.util.PricingPolicy;
import com.taxiapp.main.services.util.PricingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pricing")
@RequiredArgsConstructor
public class PricingController {

    private final PricingService pricingService;

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> updatePricing(@Valid @RequestBody UpdatePricingRequest updatePricingRequest){
        return ResponseEntity.ok(pricingService.setPricingPolicy(updatePricingRequest));
    }

    @GetMapping("/get")
    public ResponseEntity<PricingPolicy> getCurrentPricing(){
        return ResponseEntity.ok(pricingService.getPricingPolicy());
    }

}
