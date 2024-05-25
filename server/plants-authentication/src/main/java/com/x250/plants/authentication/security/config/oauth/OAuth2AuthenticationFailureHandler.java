package com.x250.plants.authentication.security.config.oauth;

import com.x250.plants.authentication.util.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.debug("onAuthenticationFailure({}, {}, {})", request.getClass(), response.getClass(), exception.toString());
        String targetUrl = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));
        log.info("Constructing target URL phase 1 done");
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();
        log.info("Constructing target URL phase 2 done");
        httpCookieOAuth2AuthorizationRequestRepository.removeCookies(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
        log.info("Target URL {} set as redirect", targetUrl);
    }
}