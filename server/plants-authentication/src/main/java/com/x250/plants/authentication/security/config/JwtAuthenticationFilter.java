package com.x250.plants.authentication.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.debug("doFilterInterval({}, {}, {})", request.getClass(), response.getClass(), filterChain.getClass());
        if (request.getServletPath().contains("/api/v1/auth")) {
            log.info("Servlet path contains /api/v1/auth");
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Authentication header is null/non-bearer");
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);


        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) { //if we have user email and user is not authenticated
            log.info("User email retrieved and authentication context is null");
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail); //we get user details from the database see ApplicationConfig

            if(jwtService.isTokenValid(jwt, userDetails)) {
                log.info("JWT is a valid token");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(// we creat object of type UsernamePassword this object is needed by Spring and by SecurityContextHolder in order to update our SecurityContext
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request) // we extend authToken with the details of our request
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Security context updated with updated authentication token");
            }
        }
        filterChain.doFilter(request, response);
        log.info("Request and response have been filtered");
    }

}
