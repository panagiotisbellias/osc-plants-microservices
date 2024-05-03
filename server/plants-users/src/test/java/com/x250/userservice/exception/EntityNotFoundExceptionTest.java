package com.x250.userservice.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EntityNotFoundExceptionTest {

    @Test
    void testConstructor() {
        EntityNotFoundException entityNotFoundException = new EntityNotFoundException("message");
        Assertions.assertEquals("message", entityNotFoundException.getMessage());
    }

}
