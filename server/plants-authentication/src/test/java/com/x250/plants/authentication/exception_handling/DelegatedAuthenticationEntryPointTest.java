package com.x250.plants.authentication.exception_handling;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.HandlerExceptionResolver;

@ExtendWith(MockitoExtension.class)
class DelegatedAuthenticationEntryPointTest {

    @InjectMocks
    DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint;

    @Mock
    HandlerExceptionResolver resolver;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    AuthenticationException authException;

    @Test
    void testCommence() {
        delegatedAuthenticationEntryPoint.commence(request, response, authException);
        Mockito.verify(resolver).resolveException(request, response, null, authException);
    }

}
