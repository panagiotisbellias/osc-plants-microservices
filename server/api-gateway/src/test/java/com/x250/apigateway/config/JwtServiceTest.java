package com.x250.apigateway.config;

import com.x250.apigateway.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Disabled("Decode argument cannot be null.")
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    JwtService jwtService;

    @Mock
    AppUserRepository appUserRepository;

    @Test
    void testConstructor() {
        JwtService jwtService = new JwtService(appUserRepository);
        Assertions.assertInstanceOf(JwtService.class, jwtService);
    }

}
