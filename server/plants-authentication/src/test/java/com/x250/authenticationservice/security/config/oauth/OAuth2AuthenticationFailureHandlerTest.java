package com.x250.authenticationservice.security.config.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class OAuth2AuthenticationFailureHandlerTest {

    @InjectMocks
    OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Mock
    HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Test
    void testConstructor() {
        OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler = new OAuth2AuthenticationFailureHandler(httpCookieOAuth2AuthorizationRequestRepository);
        Assertions.assertInstanceOf(OAuth2AuthenticationFailureHandler.class, oAuth2AuthenticationFailureHandler);
    }

    @Test
    void testOnAuthenticationFailure() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        AuthenticationException exception = Mockito.mock(AuthenticationException.class);
        oAuth2AuthenticationFailureHandler.onAuthenticationFailure(request, response, exception);

        Mockito.verify(request).getCookies();
        Mockito.verify(exception).getLocalizedMessage();
        Mockito.verify(httpCookieOAuth2AuthorizationRequestRepository).removeCookies(request, response);
        Mockito.verify(response, Mockito.times(0)).addCookie(ArgumentMatchers.any(Cookie.class));
        Mockito.verify(request).getContextPath();
        Mockito.verify(response).encodeRedirectURL(ArgumentMatchers.anyString());
        Mockito.verify(response).sendRedirect(null);
        Mockito.verifyNoMoreInteractions(response);
    }

}
