package com.x250.authenticationservice.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationRequestTest {

    @Test
    void testAuthenticationRequest() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@email.com", "password");
        Assertions.assertEquals("test@email.com", authenticationRequest.email());
        Assertions.assertEquals("password", authenticationRequest.password());
    }

}
