package com.x250.authenticationservice.security.config;

import com.x250.authenticationservice.exception_handling.DelegatedAccessDeniedHandler;
import com.x250.authenticationservice.exception_handling.DelegatedAuthenticationEntryPoint;
import com.x250.authenticationservice.security.config.oauth.CustomOauth2UserService;
import com.x250.authenticationservice.security.config.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.x250.authenticationservice.security.config.oauth.OAuth2AuthenticationFailureHandler;
import com.x250.authenticationservice.security.config.oauth.OAuth2AuthenticationSuccessHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationTest {

    @InjectMocks
    SecurityConfiguration securityConfiguration;

    @Mock
    CustomOauth2UserService customOauth2UserService;

    @Mock
    OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Mock
    OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Mock
    HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Mock
    JwtAuthenticationFilter jwtAuthFilter;

    @Mock
    AuthenticationProvider authenticationProvider;

    @Mock
    DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint;

    @Mock
    DelegatedAccessDeniedHandler delegatedAccessDeniedHandler;

    @Test
    void testConstructor() {
        SecurityConfiguration securityConfiguration = new SecurityConfiguration(customOauth2UserService,
                oAuth2AuthenticationSuccessHandler, oAuth2AuthenticationFailureHandler,
                httpCookieOAuth2AuthorizationRequestRepository, jwtAuthFilter, authenticationProvider,
                delegatedAuthenticationEntryPoint, delegatedAccessDeniedHandler);
        Assertions.assertInstanceOf(SecurityConfiguration.class, securityConfiguration);
    }

    @Test
    void testSecurityFilterChain() throws Exception {
        HttpSecurity http = Mockito.mock(HttpSecurity.class);
        Mockito.when(http.csrf(ArgumentMatchers.any(Customizer.class))).thenReturn(http);
        Mockito.when(http.cors(ArgumentMatchers.any(Customizer.class))).thenReturn(http);
        Mockito.when(http.authorizeHttpRequests(ArgumentMatchers.any(Customizer.class))).thenReturn(http);
        Mockito.when(http.sessionManagement(ArgumentMatchers.any(Customizer.class))).thenReturn(http);
        Mockito.when(http.authenticationProvider(authenticationProvider)).thenReturn(http);
        Mockito.when(http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)).thenReturn(http);
        Mockito.when(http.exceptionHandling(ArgumentMatchers.any(Customizer.class))).thenReturn(http);
        Assertions.assertNull(securityConfiguration.securityFilterChain(http));
    }

}
