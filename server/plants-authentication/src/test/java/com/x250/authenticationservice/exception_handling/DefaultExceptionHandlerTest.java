package com.x250.authenticationservice.exception_handling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
class DefaultExceptionHandlerTest {

    @InjectMocks
    DefaultExceptionHandler defaultExceptionHandler;

    @Test
    void testHandleAuthenticationException() {
        Exception ex = Mockito.mock(Exception.class);
        Mockito.when(ex.getMessage()).thenReturn("message");
        ResponseEntity<Map<String, String>> actualError = defaultExceptionHandler.handleAuthenticationException(ex);

        Assertions.assertInstanceOf(ResponseEntity.class, actualError);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, actualError.getStatusCode());
        Assertions.assertEquals(Map.of(HttpStatus.UNAUTHORIZED.toString(), "message"), actualError.getBody());
    }

    @Test
    void testHandleAuthorizationException() {
        Exception ex = Mockito.mock(Exception.class);
        Mockito.when(ex.getMessage()).thenReturn("message");
        ResponseEntity<Map<String, String>> actualError = defaultExceptionHandler.handleAuthorizationException(ex);

        Assertions.assertInstanceOf(ResponseEntity.class, actualError);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, actualError.getStatusCode());
        Assertions.assertEquals(Map.of(HttpStatus.FORBIDDEN.toString(), "message"), actualError.getBody());
    }

}
