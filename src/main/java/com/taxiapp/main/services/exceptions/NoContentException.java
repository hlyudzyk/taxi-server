package com.taxiapp.main.services.exceptions;

public class NoContentException extends RuntimeException {
    public NoContentException(String message){
        super(message);
    }
}
