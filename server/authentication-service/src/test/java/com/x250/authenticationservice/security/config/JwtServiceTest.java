package com.x250.authenticationservice.security.config;

import com.x250.authenticationservice.model.AppUser;
import com.x250.authenticationservice.repository.AppUserRepository;
import com.x250.authenticationservice.security.UserPrincipal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    JwtService jwtService;

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    AppUser appUser;

    @Mock
    Map<String, Object> extraClaims;

    @Test
    void testConstructor() {
        JwtService jwtService = new JwtService(appUserRepository);
        Assertions.assertInstanceOf(JwtService.class, jwtService);
    }

    @Disabled
    @Test
    void testExtractUsername() {
        jwtService.extractUsername("token");
    }

    @Disabled
    @Test
    void testExtractClaim() {
        Function claimsResolver = Mockito.mock(Function.class);
        jwtService.extractClaim("token", claimsResolver);
    }

    @Disabled
    @Test
    void testGenerateToken() {
        jwtService.generateToken(appUser);
    }

    @Disabled
    @Test
    void testGenerateTokenWithExtraClaimsAndAppUser() {
        jwtService.generateToken(extraClaims, appUser);
    }

    @Disabled
    @Test
    void testGenerateTokenWithAuthentication() {
        Authentication authentication = Mockito.mock(Authentication.class);
        UserPrincipal principal = Mockito.mock(UserPrincipal.class);

        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        jwtService.generateToken(extraClaims, authentication);
    }

    @Disabled
    @Test
    void testIsTokenValidWithUserDetails() {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        jwtService.isTokenValid("token", userDetails);
    }

    @Disabled
    @Test
    void testIsTokenValid() {
        jwtService.isTokenValid("token");
    }

}
