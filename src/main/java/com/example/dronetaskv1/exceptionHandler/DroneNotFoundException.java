package com.example.dronetaskv1.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DroneNotFoundException extends RuntimeException {

    public DroneNotFoundException(String message) {
        super(message);
    }
}
