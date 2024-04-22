package com.x250.apigateway.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

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
        AuthFilter authFilter = new AuthFilter(routeValidator, jwtService, userDetailsService);
        Assertions.assertInstanceOf(AuthFilter.class, authFilter);
    }

    @Disabled
    @Test
    void testFilter() {
        ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class);
        WebFilterChain chain = Mockito.mock(WebFilterChain.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        ServerHttpRequest request = Mockito.mock(ServerHttpRequest.class);

        Mockito.when(predicate.test(exchange.getRequest())).thenReturn(true);
        Mockito.when(routeValidator.isSecured).thenReturn(predicate);
        Mockito.when(exchange.getRequest()).thenReturn(request);
        authFilter.filter(exchange, chain);
    }


    @Test
    void testFilterException() {
        ServerWebExchange exchange = Mockito.mock(ServerWebExchange.class);
        WebFilterChain chain = Mockito.mock(WebFilterChain.class);
        Mockito.when(chain.filter(exchange)).thenReturn(mono);
        Assertions.assertNull(authFilter.filter(exchange, chain));
    }

}
