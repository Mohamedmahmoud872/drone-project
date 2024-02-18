package com.example.dronetaskv1.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DroneNotLoadingException extends RuntimeException {

    public DroneNotLoadingException(String message) {
        super(message);
    }

}
