package com.x250.plants.authentication.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OAuth2AuthenticationProcessingExceptionTest {

    @Test
    void testTwoArgsConstructor() {
        Throwable throwable = Mockito.mock(Throwable.class);
        Mockito.when(throwable.getMessage()).thenReturn("message2");
        OAuth2AuthenticationProcessingException oAuth2AuthenticationProcessingException = new OAuth2AuthenticationProcessingException("message1", throwable);

        Assertions.assertInstanceOf(OAuth2AuthenticationProcessingException.class, oAuth2AuthenticationProcessingException);
        Assertions.assertEquals("message1", oAuth2AuthenticationProcessingException.getMessage());
        Assertions.assertEquals("message2", oAuth2AuthenticationProcessingException.getCause().getMessage());
    }

    @Test
    void testOneArgConstructor() {
        OAuth2AuthenticationProcessingException oAuth2AuthenticationProcessingException = new OAuth2AuthenticationProcessingException("message");
        Assertions.assertInstanceOf(OAuth2AuthenticationProcessingException.class, oAuth2AuthenticationProcessingException);
        Assertions.assertEquals("message", oAuth2AuthenticationProcessingException.getMessage());
    }

}
