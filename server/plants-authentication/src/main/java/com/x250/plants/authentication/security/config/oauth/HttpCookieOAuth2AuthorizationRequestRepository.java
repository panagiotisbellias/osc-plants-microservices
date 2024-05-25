package com.x250.plants.authentication.security.config.oauth;

import com.x250.plants.authentication.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public static final String OAUTH2_AUTH_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_COOKIE_NAME = "redirect_uri";
    public static final int COOKIE_EXPIRE_SEC = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        log.debug("loadAuthorizationRequest({})", request.getClass());
        return CookieUtils.getCookie(request, OAUTH2_AUTH_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        log.debug("saveAuthorizationRequest({}, {}, {})",
                authorizationRequest != null ? authorizationRequest.getClass() : null, request.getClass(),
                response.getClass());

        if(authorizationRequest == null){
            log.warn("Authorization request is null");
            removeCookies(request, response);
            return;
        }

        CookieUtils.addCookie(response, OAUTH2_AUTH_REQUEST_COOKIE_NAME, CookieUtils.serialize(authorizationRequest), COOKIE_EXPIRE_SEC);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_COOKIE_NAME);
        if (redirectUriAfterLogin != null && !redirectUriAfterLogin.isEmpty()){
            log.info("Redirect URI after login is: {}", redirectUriAfterLogin);
            CookieUtils.addCookie(response, REDIRECT_URI_COOKIE_NAME, redirectUriAfterLogin, COOKIE_EXPIRE_SEC);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        log.debug("removeAuthorizationRequest({}, {})", request.getClass(), response.getClass());
        return this.loadAuthorizationRequest(request);
    }

    public void removeCookies(HttpServletRequest request, HttpServletResponse response){
        log.debug("removeCookies({}, {})", request.getClass(), response.getClass());
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTH_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_COOKIE_NAME);
        log.info("Cookies are deleted");
    }
}