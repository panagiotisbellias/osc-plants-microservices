package com.x250.authenticationservice.service;

import com.x250.authenticationservice.dto.AuthenticationRequest;
import com.x250.authenticationservice.dto.AuthenticationResponse;
import com.x250.authenticationservice.dto.RegisterRequest;
import com.x250.authenticationservice.model.AppUser;
import com.x250.authenticationservice.model.Role;
import com.x250.authenticationservice.repository.AppUserRepository;
import com.x250.authenticationservice.security.config.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    AuthenticationService authenticationService;

    @Mock
    AppUserRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    RegisterRequest request;

    @Mock
    AppUser appUser;

    @Mock
    Role role;

    @Test
    void testConstructor() {
        AuthenticationService authenticationService = new AuthenticationService(repository, passwordEncoder, jwtService, authenticationManager);
        Assertions.assertInstanceOf(AuthenticationService.class, authenticationService);
    }

    @Test
    void testRegister() {
        Mockito.when(request.role()).thenReturn(role);
        AuthenticationResponse response = authenticationService.register(request);
        Assertions.assertNull(response.getAccessToken());
    }

    @Test
    void testRegisterAppUserOptionalIsPresent() {
        Mockito.when(request.role()).thenReturn(role);
        Mockito.when(request.email()).thenReturn("email");
        Mockito.when(repository.findByEmail("email")).thenReturn(Optional.of(appUser));

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> authenticationService.register(request));
        Assertions.assertEquals("user already exists", runtimeException.getMessage());
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest request = Mockito.mock(AuthenticationRequest.class);
        Mockito.when(request.email()).thenReturn("email");
        Mockito.when(appUser.getRole()).thenReturn(role);
        Mockito.when(repository.findByEmail("email")).thenReturn(Optional.of(appUser));
        AuthenticationResponse response = authenticationService.authenticate(request);
        Assertions.assertNull(response.getAccessToken());
    }

    @Test
    void testValidateToken() {
        Assertions.assertFalse(authenticationService.validateToken("token"));
    }

}
