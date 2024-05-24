package com.x250.plants.api.gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter implements WebFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {

        log.debug("filter({}, {})", exchange.getClass(), chain.getClass());
        try {
            if (!RouteValidator.isSecured.test(exchange.getRequest())) {
                log.warn(String.format("Request with path %s couldn't be validated", exchange.getRequest().getPath()));
                return chain.filter(exchange);
            }

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.warn("Request doesn't contain the AUTHORIZATION header");
                return chain.filter(exchange);
            }

            String jwt = resolveToken(exchange.getRequest());
            if (jwt == null) {
                log.warn("The request's JWT is null");
                return chain.filter(exchange);
            }

            if (StringUtils.hasText(jwt) && jwtService.isTokenValid(jwt)) {
                log.info("JWT is valid");
                return Mono.fromCallable(() -> getAuthentication(jwt))
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap(authentication -> chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));

            }
            return chain.filter(exchange);
        } catch (Exception exception) {
            log.error(Arrays.toString(exception.getStackTrace()));
            return chain.filter(exchange).onErrorResume(Exception.class, ex -> Mono.fromRunnable(() ->
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
            ));
        }
    }

    private Authentication getAuthentication(String token) {
        log.debug("getAuthentication({})", token);
        String userEmail = jwtService.extractUsername(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        return new UsernamePasswordAuthenticationToken(// we create object of type UsernamePassword this object is needed by Spring and by SecurityContextHolder in order to update our SecurityContext
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    private String resolveToken(ServerHttpRequest request) {
        log.debug("resolveToken({})", request);
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.info("Bearer authorization header found");
            return bearerToken.substring(7);
        }
        log.warn("Bearer authorization header not found");
        return null;
    }
}
