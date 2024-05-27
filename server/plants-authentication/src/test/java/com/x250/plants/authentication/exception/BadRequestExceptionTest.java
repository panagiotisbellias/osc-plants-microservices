package com.x250.plants.authentication.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BadRequestExceptionTest {

    @Test
    void testOneArgsConstructor() {
        BadRequestException badRequestException = new BadRequestException("testMessage");
        Assertions.assertInstanceOf(BadRequestException.class, badRequestException);
        Assertions.assertEquals("testMessage", badRequestException.getMessage());
    }

    @Test
    void testTwoArgsConstructor() {
        Throwable throwable = Mockito.mock(Throwable.class);
        Mockito.when(throwable.getMessage()).thenReturn("message2");
        BadRequestException badRequestException = new BadRequestException("testMessage1", throwable);

        Assertions.assertInstanceOf(BadRequestException.class, badRequestException);
        Assertions.assertEquals("testMessage1", badRequestException.getMessage());
        Assertions.assertEquals("message2", badRequestException.getCause().getMessage());
    }

}
