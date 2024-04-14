package com.x250.authenticationservice.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResourceNotFoundExceptionTest {

    @Test
    void testConstructor() {
        Object object = Mockito.mock(Object.class);
        Mockito.when(object.toString()).thenReturn("field value");
        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException("resource name", "field name", object);

        Assertions.assertInstanceOf(ResourceNotFoundException.class, resourceNotFoundException);
        Assertions.assertEquals("resource name not found with field name : 'field value'", resourceNotFoundException.getMessage());
        Assertions.assertEquals("resource name", resourceNotFoundException.getResourceName());
        Assertions.assertEquals("field name", resourceNotFoundException.getFieldName());
        Assertions.assertEquals("field value", resourceNotFoundException.getFieldValue().toString());
    }

}
