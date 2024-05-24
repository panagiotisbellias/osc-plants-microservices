package com.x250.plants.api.gateway.config;


import com.x250.plants.api.gateway.repository.AppUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Log logger = LogFactory.getLog(JwtService.class);

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    private final AppUserRepository appUserRepository;

    public JwtService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public String extractUsername(String token) {
        logger.debug(String.format("extractUsername(%s)", token));
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        logger.debug(String.format("extractClaim(%s, %s)", token, claimsResolver.getClass()));
        final Claims claims = extractAllClaims(token);
        logger.info("All claims are extracted");
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token) {
        logger.debug(String.format("isTokenValid(%s)", token));
        final String email = extractUsername(token);
        logger.info(String.format("Email %s is retrieved from token", email));
        return appUserRepository.findByEmail(email).isPresent() && isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        logger.debug(String.format("isTokenExpired(%s)", token));
        return !extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        logger.debug(String.format("extractExpiration(%s)", token));
        return extractClaim(token, Claims::getExpiration);
    }
    private Claims extractAllClaims(String token) {
        logger.debug(String.format("extractAllClaims(%s)", token));
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        logger.debug("getSignInKey()");
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        logger.debug(String.format("Secret key %s is decoded to: %s", secretKey, Arrays.toString(keyBytes)));
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
