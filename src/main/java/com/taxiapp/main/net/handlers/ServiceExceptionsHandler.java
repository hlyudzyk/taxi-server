package com.taxiapp.main.net.handlers;

import com.taxiapp.main.services.exceptions.DriverToCarMatchException;
import com.taxiapp.main.services.exceptions.NoContentException;
import com.taxiapp.main.services.exceptions.UnauthorizedException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionsHandler {
    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleNoContentException(NoContentException ex) {

    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleIllegalArgumentException(IllegalArgumentException ex){

    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleBadRequestException(BadRequestException ex){

    }

    @ExceptionHandler(DriverToCarMatchException.class)
    public ResponseEntity<String> handleBadRequestException(DriverToCarMatchException ex){
        return ResponseEntity.badRequest().body("You have to assign car!");
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleAuthenticationException(UnauthorizedException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }



    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex){

        return ResponseEntity.badRequest().body(
            ex.getConstraintViolations() == null
                ?
                    ex.getMessage()
                :
                    ex.getConstraintViolations().stream().map(
                    ConstraintViolation::getMessage).toList()
        );

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errors = fieldErrors.stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .toList();
        return ResponseEntity.badRequest().body(errors);
    }


}
