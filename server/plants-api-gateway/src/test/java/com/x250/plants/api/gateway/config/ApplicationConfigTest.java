package com.x250.plants.api.gateway.config;

import com.x250.plants.api.gateway.model.AppUser;
import com.x250.plants.api.gateway.model.Role;
import com.x250.plants.api.gateway.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigTest {

    @InjectMocks
    ApplicationConfig applicationConfig;

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    AppUser appUser;

    @Mock
    Role role;

    @Test
    void testConstructor() {
        ApplicationConfig applicationConfig = new ApplicationConfig(appUserRepository);
        Assertions.assertInstanceOf(ApplicationConfig.class, applicationConfig);
    }

    @Test
    void testReactiveUserDetailsServiceTest() {
        Mockito.when(appUser.getRole()).thenReturn(Role.USER);
        Mockito.when(appUserRepository.findByEmail("testUser1")).thenReturn(Optional.of(appUser));
        Assertions.assertInstanceOf(Mono.class, applicationConfig.reactiveUserDetailsService().findByUsername("testUser1"));
    }

    @Test
    void testUserDetailsServiceTest() {
        Mockito.when(appUser.getRole()).thenReturn(Role.ADMIN);
        Mockito.when(appUserRepository.findByEmail("testUser2")).thenReturn(Optional.of(appUser));
        Assertions.assertNull(applicationConfig.userDetailsService().loadUserByUsername("testUser2").getUsername());
    }

    @Test
    void testReactiveAuthenticationManagerTest() {
        ReactiveUserDetailsService userDetailsService = Mockito.mock(ReactiveUserDetailsService.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        Assertions.assertInstanceOf(ReactiveAuthenticationManager.class, applicationConfig.reactiveAuthenticationManager(userDetailsService, passwordEncoder));
    }

    @Test
    void testPasswordEncoder() {
        Assertions.assertInstanceOf(BCryptPasswordEncoder.class, applicationConfig.passwordEncoder());
    }

}
