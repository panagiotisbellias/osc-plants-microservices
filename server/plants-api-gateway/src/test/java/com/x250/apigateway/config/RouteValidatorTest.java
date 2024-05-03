package com.x250.apigateway.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.URI;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RouteValidatorTest {

    @Test
    void testOpenEndpoints() {
        List<String> openEndpoints = RouteValidator.openEndpoints;
        Assertions.assertEquals(4, openEndpoints.size());
    }

    @Test
    void testIsSecured() {
        ServerHttpRequest request = Mockito.mock(ServerHttpRequest.class);
        Mockito.when(request.getURI()).thenReturn(URI.create("/api/v1/auth/register"));
        Assertions.assertFalse(RouteValidator.isSecured.test(request));
    }

}
