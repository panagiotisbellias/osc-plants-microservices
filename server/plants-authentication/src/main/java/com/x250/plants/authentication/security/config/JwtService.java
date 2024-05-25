package com.x250.plants.authentication.security.config;

import com.x250.plants.authentication.model.AppUser;
import com.x250.plants.authentication.repository.AppUserRepository;
import com.x250.plants.authentication.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    private final AppUserRepository appUserRepository;

    public JwtService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public String extractUsername(String token) {
        log.debug("extractUsername({})", token);
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.debug("extractClaim({}, {})", token, claimsResolver);
        final Claims claims = extractAllClaims(token);
        log.info("Claims are extracted");
        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> extraClaims, AppUser appUser) {
        return generateTokenWithLogging(extraClaims, appUser.getEmail(), appUser.getClass());
    }

    public String generateToken(Map<String, Object> extraClaims, Authentication authentication) { // method dedicated to OAuth2
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return generateTokenWithLogging(extraClaims, userPrincipal.getEmail(), authentication.toString());
    }

    private String generateTokenWithLogging(Map<String, Object> extraClaims, String subject, Object context) {
        log.debug("generateToken({}, {})", extraClaims.entrySet().toArray(), context);
        return generateToken(extraClaims, subject);
    }

    private String generateToken(Map<String, Object> extraClaims, String subject) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.debug("isTokenValid({}, {})", token, userDetails.getClass());
        final String username = extractUsername(token);
        log.info("Extracted username: {}", username);
        return (username.equals(userDetails.getUsername())) && isTokenExpired(token);
    }

    public boolean isTokenValid(String token) {
        log.debug("isTokenValid({})", token);
        final String email = extractUsername(token);
        log.info("Extracted email: {}", email);
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
        log.info("Secret key {} is decoded to: {}", secretKey, Arrays.toString(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
