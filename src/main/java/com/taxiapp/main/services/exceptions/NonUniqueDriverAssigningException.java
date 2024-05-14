package com.taxiapp.main.services.exceptions;

public class NonUniqueDriverAssigningException extends RuntimeException{
    public NonUniqueDriverAssigningException(String message){
        super(message);
    }
}
