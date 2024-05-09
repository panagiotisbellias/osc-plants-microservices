package com.x250.plants.api.gateway.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class AuthFilterTest {

    @InjectMocks
    AuthFilter authFilter;

    @Mock
    RouteValidator routeValidator;

    @Mock
    JwtService jwtService;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    Mono<Void> mono;

    @Test
    void testConstructor() {
        AuthFilter authFilter = new AuthFilter(jwtService, userDetailsService);
        Assertions.assertInstanceOf(AuthFilter.class, authFilter);
    }

    @Test
    void testFilterException() {
        ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class);
        WebFilterChain chain = Mockito.mock(WebFilterChain.class);
        Mockito.when(chain.filter(exchange)).thenReturn(mono);
        Assertions.assertNull(authFilter.filter(exchange, chain));
    }

}
