package com.x250.plants.authentication.exception_handling;

import com.x250.plants.authentication.exception.BadRequestException;
import com.x250.plants.authentication.exception.CaptchaVerificationException;
import com.x250.plants.authentication.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    private static final String MAP_KEY = "message";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.debug("handleValidationExceptions({})", ex.toString());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            log.info("Error: {}", error.getClass().getName());
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logErrors(errors);
        return errors;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CaptchaVerificationException.class)
    public Map<String, String> handleCaptchaVerificationExceptions(
            CaptchaVerificationException ex) {
        log.debug("handleCaptchaVerificationExceptions({})", ex.toString());
        return createErrorResponse(ex);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleResourceNotFoundExceptions(
            ResourceNotFoundException ex) {
        log.debug("handleResourceNotFoundExceptions({})", ex.toString());
        return createErrorResponse(ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public Map<String, String> handleBadRequestExceptions(
            BadRequestException ex) {
        log.debug("handleBadRequestExceptions({})", ex.toString());
        return createErrorResponse(ex);
    }

    private Map<String, String> createErrorResponse(Exception ex) {
        log.debug("createErrorResponse({})", ex.toString());
        Map<String, String> errors = new HashMap<>();
        errors.put(MAP_KEY, ex.getMessage());
        logErrors(errors);
        return errors;
    }

    private void logErrors(Map<String, String> errors) {
        log.debug("logErrors({})", errors);
        log.info("Errors: {}", errors.entrySet().toArray());
    }

}
