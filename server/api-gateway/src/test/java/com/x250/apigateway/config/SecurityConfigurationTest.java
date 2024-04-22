package com.x250.apigateway.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationTest {

    @Mock
    AuthFilter authFilter;

    @Test
    void testConstructor() {
        SecurityConfiguration securityConfiguration = new SecurityConfiguration(authFilter);
        Assertions.assertInstanceOf(SecurityConfiguration.class, securityConfiguration);
    }

}
