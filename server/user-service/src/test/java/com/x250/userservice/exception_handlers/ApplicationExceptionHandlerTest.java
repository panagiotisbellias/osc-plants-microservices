package com.x250.userservice.exception_handlers;

import com.x250.userservice.exception.EntityNotFoundException;
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
        Map<String, String> errorMap = applicationExceptionHandler.handleEntityNotFoundException(ex);

        Assertions.assertEquals(1, errorMap.size());
        Assertions.assertNull(errorMap.get("errorMessage"));
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException ex = Mockito.mock(RuntimeException.class);
        Map<String, String> errorMap = applicationExceptionHandler.handleRuntimeException(ex);

        Assertions.assertEquals(1, errorMap.size());
        Assertions.assertNull(errorMap.get("errorMessage"));
    }

}
