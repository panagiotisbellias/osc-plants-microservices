package com.x250.apigateway.exception_handler;

import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ServerWebExchange;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationEntryPointTest {

    @InjectMocks
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Mock
    ServerWebExchange exchange;

    @Mock
    ServerHttpResponse response;

    @Mock
    HttpHeaders headers;

    @Mock
    DataBufferFactory bufferFactory;

    @Mock
    DataBuffer buffer;

    @BeforeEach
    public void setUp() {
        Mockito.when(response.getHeaders()).thenReturn(headers);
        Mockito.when(bufferFactory.wrap(ArgumentMatchers.any(byte[].class))).thenReturn(buffer);
        Mockito.when(response.bufferFactory()).thenReturn(bufferFactory);
        Mockito.when(exchange.getResponse()).thenReturn(response);
    }

    @Test
    void testHandleSignatureException() {
        SignatureException ex = Mockito.mock(SignatureException.class);
        Assertions.assertNull(customAuthenticationEntryPoint.handleSignatureException(exchange, ex));
    }

    @Test
    void testCommence() {
        AuthenticationException authException = Mockito.mock(AuthenticationException.class);
        Assertions.assertNull(customAuthenticationEntryPoint.commence(exchange, authException));
    }

}
