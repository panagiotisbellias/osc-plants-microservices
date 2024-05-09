package com.x250.plants.api.gateway.exception_handler;

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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ServerWebExchange;

@ExtendWith(MockitoExtension.class)
class CustomAccessDeniedHandlerTest {

    @InjectMocks
    CustomAccessDeniedHandler customAccessDeniedHandler;

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
        Assertions.assertNull(customAccessDeniedHandler.handleSignatureException(exchange, ex));
    }

    @Test
    void testHandle() {
        AccessDeniedException denied = Mockito.mock(AccessDeniedException.class);
        Assertions.assertNull(customAccessDeniedHandler.handle(exchange, denied));
    }

}
