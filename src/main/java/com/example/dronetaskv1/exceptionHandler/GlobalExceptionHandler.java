package com.example.dronetaskv1.exceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DroneNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleDroneNotFound(DroneNotFoundException exc) {
        List<String> errors = Collections.singletonList(exc.getMessage());

        Map<String, List<String>> errorResponse = new HashMap<>();

        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DroneNotLoadingException.class)
    public ResponseEntity<Map<String, List<String>>> handleDroneNotLoading(DroneNotLoadingException exc) {
        List<String> errors = Collections.singletonList(exc.getMessage());

        Map<String, List<String>> errorResponse = new HashMap<>();

        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DroneWeightExeededException.class)
    public ResponseEntity<Map<String, List<String>>> handleDroneFull(DroneWeightExeededException exc) {
        List<String> errors = Collections.singletonList(exc.getMessage());

        Map<String, List<String>> errorResponse = new HashMap<>();

        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MedicationExistsException.class)
    public ResponseEntity<Map<String, List<String>>> handleMedicationExistsException(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());

        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // to handle database constraints exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidArguments(MethodArgumentNotValidException exc) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        
        List<String> errors = exc.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    

}
