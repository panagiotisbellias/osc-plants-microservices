package com.x250.plants.api.gateway.config;


import com.x250.plants.api.gateway.repository.AppUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    private final AppUserRepository appUserRepository;

    public JwtService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public String extractUsername(String token) {
        log.debug("extractUsername({})", token);
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.debug("extractClaim({}, {})", token, claimsResolver.getClass());
        final Claims claims = extractAllClaims(token);
        log.info("All claims are extracted");
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token) {
        log.debug("isTokenValid({})", token);
        final String email = extractUsername(token);
        log.info("Email {} is retrieved from token", email);
        return appUserRepository.findByEmail(email).isPresent() && isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        log.debug("isTokenExpired({})", token);
        return !extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        log.debug("extractExpiration({})", token);
        return extractClaim(token, Claims::getExpiration);
    }
    private Claims extractAllClaims(String token) {
        log.debug("extractAllClaims({})", token);
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        log.debug("getSignInKey()");
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        log.debug("Secret key {} is decoded to: {}", secretKey, Arrays.toString(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
