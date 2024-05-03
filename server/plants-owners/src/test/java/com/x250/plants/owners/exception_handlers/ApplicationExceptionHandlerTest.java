package com.x250.plants.owners.exception_handlers;

import com.x250.plants.owners.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
class ApplicationExceptionHandlerTest {

    @InjectMocks
    ApplicationExceptionHandler applicationExceptionHandler;

    @Mock
    EntityNotFoundException ex;

    @Test
    void testHandleEntityNotFoundException() {
        Map<String, String> errorMap = applicationExceptionHandler.handleEntityNotFoundException(ex);
        Assertions.assertNull(errorMap.get("errorMessage"));
    }

    @Test
    void testHandleInterruptedException() {
        Map<String, String> errorMap = applicationExceptionHandler.handleInterruptedException(ex);
        Assertions.assertNull(errorMap.get("errorMessage"));
    }

}
