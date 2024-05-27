package com.x250.plants.owners.exception_handlers;


import com.x250.plants.owners.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.debug("handleEntityNotFoundException({})", ex.toString());
        return createErrorMap(ex);
    }

    @ResponseStatus(HttpStatus.NOT_EXTENDED)
    @ExceptionHandler(InterruptedException.class)
    public Map<String, String> handleInterruptedException(EntityNotFoundException ex) {
        log.debug("handleInterruptedException({})", ex.toString());
        return createErrorMap(ex);
    }

    private Map<String, String> createErrorMap(EntityNotFoundException ex) {
        log.debug("createErrorMap({})", ex.toString());
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        log.info("Errors: {}", errorMap.entrySet().toArray());
        return errorMap;
    }
}
