package com.x250.plants.authentication.exception_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleAuthenticationException(Exception ex) {
        log.debug("handleAuthenticationException({})", ex.toString());
        return handleException(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AuthorizationServiceException.class})
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleAuthorizationException(Exception ex) {
        log.debug("handleAuthorizationException({})", ex.toString());
        return handleException(ex, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<Map<String, String>> handleException(Exception ex, HttpStatus status) {
        log.debug("handleException({}, {})", ex.toString(), status.toString());
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(status.toString(), ex.getMessage());
        log.info("Errors: {}", errorMap.entrySet().toArray());
        return ResponseEntity.status(status).body(errorMap);
    }

}