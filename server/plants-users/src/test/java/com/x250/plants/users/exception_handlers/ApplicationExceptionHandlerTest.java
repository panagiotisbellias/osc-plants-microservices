package com.x250.plants.users.exception_handlers;

import com.x250.plants.users.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
class ApplicationExceptionHandlerTest {

    @InjectMocks
    ApplicationExceptionHandler applicationExceptionHandler;

    @Test
    void testHandleEntityNotFoundException() {
        EntityNotFoundException ex = Mockito.mock(EntityNotFoundException.class);
        Mockito.when(ex.getMessage()).thenReturn("testMessage2");
        Map<String, String> errorMap = applicationExceptionHandler.handleEntityNotFoundException(ex);

        Assertions.assertEquals(1, errorMap.size());
        Assertions.assertEquals("testMessage2", errorMap.get("errorMessage"));
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException ex = Mockito.mock(RuntimeException.class);
        Mockito.when(ex.getMessage()).thenReturn("testMessage1");
        Map<String, String> errorMap = applicationExceptionHandler.handleRuntimeException(ex);

        Assertions.assertEquals(1, errorMap.size());
        Assertions.assertEquals("testMessage1", errorMap.get("errorMessage"));
    }

}
