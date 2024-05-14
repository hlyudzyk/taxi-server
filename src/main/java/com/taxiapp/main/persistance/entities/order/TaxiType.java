package com.taxiapp.main.persistance.entities.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaxiType {
    BASIC,
    PREMIUM,
    CARGO;
}