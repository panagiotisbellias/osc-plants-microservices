package com.x250.authenticationservice.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    JwtService jwtService;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @Test
    void testConstructor() {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        Assertions.assertInstanceOf(JwtAuthenticationFilter.class, jwtAuthenticationFilter);
        Assertions.assertInstanceOf(Environment.class, jwtAuthenticationFilter.getEnvironment());
        Assertions.assertNull(jwtAuthenticationFilter.getFilterConfig());
    }

    @Test
    void testDoFilterInternal() throws ServletException, IOException {
        Mockito.when(request.getServletPath()).thenReturn("servlet path");
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer header");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verifyCommonInvocations();

        Mockito.verify(request).getHeader("Authorization");
        Mockito.verify(jwtService).extractUsername("header");
        Mockito.verifyNoMoreInteractions(jwtService);
    }

    @Test
    void testDoFilterIntervalServletPathApiV1Auth() throws ServletException, IOException {
        Mockito.when(request.getServletPath()).thenReturn("/api/v1/auth");
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verifyCommonInvocations();
        Mockito.verifyNoMoreInteractions(request);
        Mockito.verifyNoInteractions(jwtService);
    }

    @Test
    void testDoFilterIntervalAuthHeaderNull() throws ServletException, IOException {
        Mockito.when(request.getServletPath()).thenReturn("servlet path");
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verifyCommonInvocations();
        Mockito.verify(request).getHeader("Authorization");
        Mockito.verifyNoInteractions(jwtService);
    }

    @Test
    void testDoFilterIntervalAuthHeaderNoBearer() throws ServletException, IOException {
        Mockito.when(request.getServletPath()).thenReturn("servlet path");
        Mockito.when(request.getHeader("Authorization")).thenReturn("header");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verifyCommonInvocations();
        Mockito.verify(request).getHeader("Authorization");
        Mockito.verifyNoInteractions(jwtService);
    }

    void verifyCommonInvocations() throws ServletException, IOException {
        Mockito.verify(request).getServletPath();
        Mockito.verify(filterChain).doFilter(request, response);
        Mockito.verifyNoInteractions(userDetailsService);
    }

    @Disabled("SecurityContext should be mocked")
    @Test
    void testDoFilterIntervalUserEmailNotNull() throws ServletException, IOException {
        Mockito.when(request.getServletPath()).thenReturn("servlet path");
        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer header");
        Mockito.when(jwtService.extractUsername("header")).thenReturn("user email");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        Mockito.verify(request).getServletPath();
        Mockito.verify(filterChain).doFilter(request, response);
        Mockito.verify(request).getHeader("Authorization");
        Mockito.verify(jwtService).extractUsername("header");
        Mockito.verifyNoInteractions(userDetailsService);
        Mockito.verifyNoMoreInteractions(jwtService);
    }

}
