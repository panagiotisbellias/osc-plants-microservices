package com.x250.plants.authentication.security.config;

import com.x250.plants.authentication.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    @Test
    void testConstructor() {
        JwtService jwtService = new JwtService(appUserRepository);
        Assertions.assertInstanceOf(JwtService.class, jwtService);
    }

}
