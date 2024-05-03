package com.x250.authenticationservice.exception_handling;

import com.x250.authenticationservice.exception.BadRequestException;
import com.x250.authenticationservice.exception.CaptchaVerificationException;
import com.x250.authenticationservice.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class ApplicationExceptionHandlerTest {

    @InjectMocks
    ApplicationExceptionHandler applicationExceptionHandler;

    @Test
    void testHandleValidationExceptions() {
        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        FieldError error = Mockito.mock(FieldError.class);

        Mockito.when(error.getField()).thenReturn("field");
        Mockito.when(error.getDefaultMessage()).thenReturn("default message");
        Mockito.when(bindingResult.getAllErrors()).thenReturn(List.of(error));
        Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);

        Map<String, String> actualErrors = applicationExceptionHandler.handleValidationExceptions(ex);
        Assertions.assertEquals(1, actualErrors.size());
        Assertions.assertEquals(Map.of("field", "default message").get("field"), actualErrors.get("field"));
    }

    @Test
    void testHandleCaptchaVerificationExceptions() {
        CaptchaVerificationException ex = Mockito.mock(CaptchaVerificationException.class);
        Mockito.when(ex.getMessage()).thenReturn("test");
        Map<String, String> errors = applicationExceptionHandler.handleCaptchaVerificationExceptions(ex);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("test", errors.get("message"));
    }

    @Test
    void testHandleResourceNotFoundExceptions() {
        ResourceNotFoundException ex = Mockito.mock(ResourceNotFoundException.class);
        Mockito.when(ex.getMessage()).thenReturn("test");
        Map<String, String> errors = applicationExceptionHandler.handleResourceNotFoundExceptions(ex);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("test", errors.get("message"));
    }

    @Test
    void testHandleBadRequestExceptions() {
        BadRequestException ex = Mockito.mock(BadRequestException.class);
        Mockito.when(ex.getMessage()).thenReturn("test");
        Map<String, String> errors = applicationExceptionHandler.handleBadRequestExceptions(ex);

        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("test", errors.get("message"));
    }

}
