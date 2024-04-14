package com.x250.authenticationservice.security.config.oauth;

import com.x250.authenticationservice.security.UserPrincipal;
import com.x250.authenticationservice.security.config.JwtService;
import jakarta.servlet.ServletException;
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
import org.springframework.security.core.Authentication;

import java.io.IOException;
//import java.util.List;

@ExtendWith(MockitoExtension.class)
class OAuth2AuthenticationSuccessHandlerTest {

    @InjectMocks
    OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Mock
    JwtService jwtService;

    @Mock
    HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    Authentication authentication;

    @Mock
    UserPrincipal principal;

//    @Mock
//    List<String> urisList;

    @Test
    void testConstructor() {
        OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler = new OAuth2AuthenticationSuccessHandler(jwtService, httpCookieOAuth2AuthorizationRequestRepository);
        Assertions.assertInstanceOf(OAuth2AuthenticationSuccessHandler.class, oAuth2AuthenticationSuccessHandler);
    }

    @Test
    void testOnAuthenticationSuccess() throws ServletException, IOException {
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        oAuth2AuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);

        verifyOnAuthenticationSuccessInvocations();
        Mockito.verify(request).getSession(false);
        Mockito.verify(request).getContextPath();
        Mockito.verify(response).encodeRedirectURL(ArgumentMatchers.anyString());
        Mockito.verify(response).sendRedirect(null);
    }

    @Test
    void testOnAuthenticationSuccessResponseCommitted() throws ServletException, IOException {
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        Mockito.when(response.isCommitted()).thenReturn(true);
        oAuth2AuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);

        verifyOnAuthenticationSuccessInvocations();
        Mockito.verify(request, Mockito.times(0)).getSession(false);
        Mockito.verify(request, Mockito.times(0)).getContextPath();
        Mockito.verify(response, Mockito.times(0)).encodeRedirectURL(ArgumentMatchers.anyString());
        Mockito.verify(response, Mockito.times(0)).sendRedirect(null);
    }

    void verifyOnAuthenticationSuccessInvocations() throws IOException {
        verifyCommonInvocations();
        Mockito.verify(response).isCommitted();
        Mockito.verify(response, Mockito.times(0)).addCookie(ArgumentMatchers.any(Cookie.class));
        Mockito.verify(response, Mockito.times(0)).setHeader(ArgumentMatchers.eq("Location"), ArgumentMatchers.anyString());
        Mockito.verify(response, Mockito.times(0)).setStatus(ArgumentMatchers.anyInt());
        Mockito.verify(response, Mockito.times(0)).getWriter();
    }

    @Test
    void testDetermineTargetUrl() {
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        oAuth2AuthenticationSuccessHandler.determineTargetUrl(request, response, authentication);
        verifyCommonInvocations();
    }

    void verifyCommonInvocations() {
        Mockito.verify(request).getCookies();
        Mockito.verify(authentication).getPrincipal();
        Mockito.verify(principal).getId();
        Mockito.verify(principal).getName();
        Mockito.verify(principal).getAttributes();
        Mockito.verify(jwtService).generateToken(ArgumentMatchers.anyMap(), ArgumentMatchers.eq(authentication));
        Mockito.verify(principal, Mockito.times(0)).getEmail();
    }

//    @Test
//    void testDetermineTargetUrlUnauthorizedRedirectURI() {
//        Cookie cookie = Mockito.mock(Cookie.class);
//        Mockito.when(cookie.getName()).thenReturn("redirect_uri");
//        Mockito.when(cookie.getValue()).thenReturn("value");

//        Cookie[] cookies = new Cookie[]{cookie};
//        Mockito.when(request.getCookies()).thenReturn(cookies);
//        oAuth2AuthenticationSuccessHandler.determineTargetUrl(request, response, authentication);
//    }

}
