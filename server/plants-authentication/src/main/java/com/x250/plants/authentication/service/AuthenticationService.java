package com.x250.plants.authentication.service;

import com.x250.plants.authentication.dto.AuthenticationRequest;
import com.x250.plants.authentication.dto.AuthenticationResponse;
import com.x250.plants.authentication.dto.RegisterRequest;
import com.x250.plants.authentication.model.AppUser;
import com.x250.plants.authentication.repository.AppUserRepository;
import com.x250.plants.authentication.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        log.debug("register({})", request.getClass());
        AppUser user = AppUser.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();
        log.info("App user instantiated");
        Optional<AppUser> appUserOptional = repository.findByEmail(request.email());
        if (appUserOptional.isPresent()) {
            log.error("User already exists");
            throw new RuntimeException("user already exists");
        }

        log.info("App user is created");
        repository.save(user);

        return createAuthenticationResponse(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.debug("authenticate({})", request.getClass());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        AppUser user = repository.findByEmail(request.email())
                .orElseThrow();
        log.info("Found app user with email: {}", request.email());
        return createAuthenticationResponse(user);
    }

    private AuthenticationResponse createAuthenticationResponse(AppUser user) {
        Map<String, Object> claims = getClaims(user);
        log.info("Claims are: {}", claims.entrySet().toArray());
        String jwtToken = jwtService.generateToken(claims, user);
        log.debug("JWT Token is {}", jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public Boolean validateToken(String token) {
        log.debug("validateToken({})", token);
        return jwtService.isTokenValid(token);
    }

    private Map<String, Object> getClaims(AppUser user) {
        log.debug("getClaims({})", user.toString());
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("imageUrl", user.getImageUrl());
        claims.put("role", user.getRole().name());
        log.info("Claims set: {}", claims.entrySet().toArray());
        return claims;
    }
}
