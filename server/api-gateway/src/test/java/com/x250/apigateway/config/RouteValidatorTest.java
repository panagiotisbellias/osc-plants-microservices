package com.x250.apigateway.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.URI;
import java.util.List;
import java.util.function.Predicate;

@ExtendWith(MockitoExtension.class)
class RouteValidatorTest {

    @InjectMocks
    RouteValidator routeValidator;

    @Test
    void testOpenEndpoints() {
        List<String> openEndpoints = RouteValidator.openEndpoints;
        Assertions.assertEquals(4, openEndpoints.size());
    }

    @Test
    void testIsSecured() {
        ServerHttpRequest request = Mockito.mock(ServerHttpRequest.class);
        Mockito.when(request.getURI()).thenReturn(URI.create("/api/v1/auth/register"));
        Assertions.assertFalse(routeValidator.isSecured.test(request));
    }

}
