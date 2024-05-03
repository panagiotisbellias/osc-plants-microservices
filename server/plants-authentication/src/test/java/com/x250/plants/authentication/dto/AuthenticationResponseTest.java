package com.x250.plants.authentication.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationResponseTest {

    @Test
    void testAllArgsConstructor() {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("access token");
        Assertions.assertEquals("access token", authenticationResponse.getAccessToken());
    }

    @Test
    void testNoArgsConstructor() {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        Assertions.assertInstanceOf(AuthenticationResponse.class, authenticationResponse);
    }

    @Test
    void testSetters() {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken("access token");
        Assertions.assertEquals("access token", authenticationResponse.getAccessToken());
    }

}
