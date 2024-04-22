package com.x250.apigateway.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.cors.reactive.CorsWebFilter;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationTest {

    @Mock
    AuthFilter authFilter;

    @Mock
    CorsWebFilter corsWebFilter;

    @Test
    void testConstructor() {
        SecurityConfiguration securityConfiguration = new SecurityConfiguration(authFilter, corsWebFilter);
        Assertions.assertInstanceOf(SecurityConfiguration.class, securityConfiguration);
    }

}
