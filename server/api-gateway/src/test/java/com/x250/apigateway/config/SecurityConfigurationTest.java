package com.x250.apigateway.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.web.cors.reactive.CorsWebFilter;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationTest {

    @InjectMocks
    SecurityConfiguration securityConfiguration;

    @Mock
    AuthFilter authFilter;

    @Mock
    CorsWebFilter corsWebFilter;

    @Test
    void testConstructor() {
        SecurityConfiguration securityConfiguration = new SecurityConfiguration(authFilter, corsWebFilter);
        Assertions.assertInstanceOf(SecurityConfiguration.class, securityConfiguration);
    }

    @Disabled("Underlying exception : org.mockito.exceptions.base.MockitoException: Could not modify all classes [class org.springframework.security.config.web.server.ServerHttpSecurity]")
    @Test
    void testSpringSecurityFilterChain() {
        ServerHttpSecurity serverHttpSecurity = Mockito.mock(ServerHttpSecurity.class);
        securityConfiguration.springSecurityFilterChain(serverHttpSecurity);
    }

}
