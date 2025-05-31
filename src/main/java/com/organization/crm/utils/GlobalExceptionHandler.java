package com.organization.crm.utils;

import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Related to credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(
      BadCredentialsException e,
      WebRequest request
    ){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createErrorMap(request.getDescription(false), e.getMessage()));
    }

    // Overall BadRequest
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(
            BadRequestException e,
            WebRequest request
    ){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createErrorMap(request.getDescription(false), e.getMessage()));
    }

    // Internal Server Error
    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<Map<String, Object>> handleInternalServerError(
            InternalServerError e, WebRequest request
    ) {
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        createErrorMap(request.getDescription(false), "Internal Server Error")
                );
    }

    // Notfound Exceptions
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(
            ChangeSetPersister.NotFoundException e,
            WebRequest request
    ){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(createErrorMap(request.getDescription(false), e.getMessage()));
    }

    // Remaining Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception e, WebRequest request) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorMap(request.getDescription(false), e.getMessage()));
    }

    // Not Readable Exception
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleNotReadablePropertyException(
            HttpMessageNotReadableException e,
            WebRequest request
    ){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createErrorMap(request.getDescription(false), e.getMessage()));
    }

    // Validation in controllers | NOT IN RECORD
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
            ConstraintViolationException e, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createErrorMap(
                        request.getDescription(false),
                        e.getMessage()
                ));
    }

    // Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException e
    ){
        Map<String, Object> errors = new HashMap<>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach((error) -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    private Map<String, Object> createErrorMap(String path, String message) {
        Map<String, Object> errors = new HashMap<>();

        errors.put("message", message);
        errors.put("path", path);
        errors.put("timestamp", LocalDateTime.now());

        return errors;
    }
}
