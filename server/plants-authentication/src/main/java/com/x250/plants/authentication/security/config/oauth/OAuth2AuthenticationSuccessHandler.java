package com.x250.plants.authentication.security.config.oauth;

import com.x250.plants.authentication.exception.BadRequestException;
import com.x250.plants.authentication.security.UserPrincipal;
import com.x250.plants.authentication.security.config.JwtService;
import com.x250.plants.authentication.util.CookieUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Value("${application.authorizedRedirectUris}")
    private String authorizedRedirectUris;

    private List<String> urisList;

    @PostConstruct
    private void init() {
        log.debug("init()");
        urisList = Arrays.asList(authorizedRedirectUris.split(","));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.debug("onAuthenticationSuccess({}, {}, {})", request.getClass(), response.getClass(), authentication.getClass());
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.warn("Response has already been committed. Unable to redirect to {}", targetUrl);
            return;
        }

        log.info("Response hasn't been committed");
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
        log.info("Redirecting to {}", targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.debug("determineTargetUrl({}, {}, {})", request.getClass(), response.getClass(), authentication.getClass());
        Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            log.error("Unauthorized Redirect URI. Can't proceed with the authentication");
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        log.info("Authorized Redirect URI");
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        log.info("Proceeding with target URL: {}", targetUrl);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userPrincipal.getId());
        claims.put("username", userPrincipal.getName()); // error
        claims.put("imageUrl", userPrincipal.getAttributes().get("picture"));
        claims.put("role", "USER");
        log.info("Claims are constructed");
        String token = jwtService.generateToken(claims, authentication);
        log.debug("Generated token: {}", token);
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        log.debug("clearAuthenticationAttributes({}, {})", request.getClass(), response.getClass());
        super.clearAuthenticationAttributes(request);
        log.info("Cleared authentication attributes from request");
        httpCookieOAuth2AuthorizationRequestRepository.removeCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        log.debug("isAuthorizedRedirectUri({})", uri);
        URI clientRedirectUri = URI.create(uri);
        log.info("Client redirect URI: {}", clientRedirectUri);
        return urisList
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    log.info("Authorized Redirect URI: {}", authorizedRedirectUri);
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    log.info("Authorized URI: {}", authorizedURI);
                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort();
                });
    }
}