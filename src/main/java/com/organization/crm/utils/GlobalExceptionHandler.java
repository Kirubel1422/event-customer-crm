package com.organization.crm.utils;

import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Related to credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(
      BadCredentialsException e
    ){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createErrorMap(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    // Overall BadRequest
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(
            BadRequestException e
    ){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createErrorMap(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    // Internal Server Error
    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<Map<String, String>> handleInternalServerError(InternalServerError e){
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        createErrorMap(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage())
                );
    }

    // Notfound Exceptions
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(ChangeSetPersister.NotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(createErrorMap(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    // Remaining Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorMap(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    // Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException e
    ){
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach((error) -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    private Map<String, String> createErrorMap(int status, String message) {
        Map<String, String> errors = new HashMap<>();

        errors.put("message", message);
        errors.put("status", String.valueOf(status));
        errors.put("timestamp", String.valueOf(System.currentTimeMillis()));

        return errors;
    }
}
