package com.x250.plants.authentication.security.config.oauth;

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
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@ExtendWith(MockitoExtension.class)
class HttpCookieOAuth2AuthorizationRequestRepositoryTest {

    @InjectMocks
    HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    OAuth2AuthorizationRequest authorizationRequest;

    @Test
    void testLoadAuthorizationRequest() {
        Assertions.assertNull(httpCookieOAuth2AuthorizationRequestRepository.loadAuthorizationRequest(request));
    }

    @Test
    void testSaveAuthorizationRequest() {
        Mockito.when(request.getParameter(HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_COOKIE_NAME)).thenReturn("redirect uri after login");
        httpCookieOAuth2AuthorizationRequestRepository.saveAuthorizationRequest(authorizationRequest, request, response);

        verifyRequestInvocations();
        Mockito.verify(response, Mockito.times(2)).addCookie(ArgumentMatchers.any(Cookie.class));
    }

    @Test
    void testSaveAuthorizationRequestAuthorizationRequestNull() {
        httpCookieOAuth2AuthorizationRequestRepository.saveAuthorizationRequest(null, request, response);
        verifyRequestResponseInvocationsRemoveCookies();
        Mockito.verifyNoMoreInteractions(request);
    }

    @Test
    void testSaveAuthorizationRequestNullRedirectUriAfterLogin() {
        httpCookieOAuth2AuthorizationRequestRepository.saveAuthorizationRequest(authorizationRequest, request, response);
        verifyRequestResponseInvocations();
    }

    @Test
    void testSaveAuthorizationRequestEmptyRedirectUriAfterLogin() {
        Mockito.when(request.getParameter(HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_COOKIE_NAME)).thenReturn("");
        httpCookieOAuth2AuthorizationRequestRepository.saveAuthorizationRequest(authorizationRequest, request, response);
        verifyRequestResponseInvocations();
    }

    void verifyRequestResponseInvocations() {
        verifyRequestInvocations();
        Mockito.verify(response).addCookie(ArgumentMatchers.any(Cookie.class));
        Mockito.verifyNoMoreInteractions(response);
    }

    void verifyRequestInvocations() {
        Mockito.verify(request, Mockito.times(0)).getCookies();
        Mockito.verify(request).getParameter(HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_COOKIE_NAME);
    }

    @Test
    void testRemoveAuthorizationRequest() {
        Assertions.assertNull(httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequest(request, response));
    }

    @Test
    void testRemoveCookies() {
        httpCookieOAuth2AuthorizationRequestRepository.removeCookies(request, response);
        verifyRequestResponseInvocationsRemoveCookies();
    }

    void verifyRequestResponseInvocationsRemoveCookies() {
        Mockito.verify(request, Mockito.times(2)).getCookies();
        Mockito.verifyNoInteractions(response);
    }

}
