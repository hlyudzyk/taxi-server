package com.taxiapp.main.services.exceptions;

public class LocationGeocodingException extends RuntimeException{

    public LocationGeocodingException(String message) {
        super(message);
    }
}
