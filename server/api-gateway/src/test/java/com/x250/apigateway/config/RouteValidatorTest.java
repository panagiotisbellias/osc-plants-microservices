package com.x250.apigateway.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Assertions.assertInstanceOf(Predicate.class, routeValidator.isSecured);
    }

}
