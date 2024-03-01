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

    /**
     * Handles DroneNotFound Exception
     *
     * @param exc error exception
     * @return Response Entity with NOT_FOUND code and Map of Errors
     */
    @ExceptionHandler(DroneNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleDroneNotFound(DroneNotFoundException exc) {
        List<String> errors = Collections.singletonList(exc.getMessage());

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles DroneNotLoading Exception
     * @param exc error exception
     * @return Response Entity with NOT_FOUND code and Map of Errors
     */
    @ExceptionHandler(DroneNotLoadingException.class)
    public ResponseEntity<Map<String, List<String>>> handleDroneNotLoading(DroneNotLoadingException exc) {
        List<String> errors = Collections.singletonList(exc.getMessage());

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles DroneWeightExceeded Exception
     * @param exc error exception
     * @return Response Entity with BAD_REQUEST code and Map of Errors
     */
    @ExceptionHandler(DroneWeightExeededException.class)
    public ResponseEntity<Map<String, List<String>>> handleDroneFull(DroneWeightExeededException exc) {
        List<String> errors = Collections.singletonList(exc.getMessage());

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MedicationExists Exception
     * @param ex error exception
     * @return Response Entity with BAD_REQUEST code and Map of Errors
     */
    @ExceptionHandler(MedicationExistsException.class)
    public ResponseEntity<Map<String, List<String>>> handleMedicationExistsException(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles database constraints Exceptions
     * @param exc error exception
     * @return Response Entity with BAD_REQUEST code and Map of Errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidArguments(MethodArgumentNotValidException exc) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        
        List<String> errors = exc.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles General Exceptions
     *
     * @param ex The General Exception
     * @return Response Entity with Internal server error code and Map of Errors
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles RunTime Exceptions
     *
     * @param ex The RunTime Exception
     * @return Response Entity with Internal server error code and Map of Errors
     */
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Creates Error Response Format
     *
     * @param errors The list of errors
     * @return Map of the errors
     */
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
    

}
