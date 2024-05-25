package com.x250.plants.authentication.security.config;

import com.x250.plants.authentication.model.AppUser;
import com.x250.plants.authentication.model.Role;
import com.x250.plants.authentication.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigTest {

    @InjectMocks
    ApplicationConfig applicationConfig;

    @Mock
    AppUserRepository appUserRepository;

    @Test
    void testConstructor() {
        ApplicationConfig applicationConfig = new ApplicationConfig(appUserRepository);
        Assertions.assertInstanceOf(ApplicationConfig.class, applicationConfig);
    }

    @Test
    void testUserDetailsService() {
        AppUser appUser = Mockito.mock(AppUser.class);
        Role role = Mockito.mock(Role.class);

        Mockito.when(appUser.getRole()).thenReturn(role);
        Mockito.when(appUserRepository.findByEmail("username")).thenReturn(Optional.of(appUser));
        UserDetails userDetails = applicationConfig.userDetailsService().loadUserByUsername("username");
        Assertions.assertNull(userDetails.getPassword());
        Assertions.assertNull(userDetails.getUsername());
        Assertions.assertTrue(userDetails.isAccountNonExpired());
        Assertions.assertTrue(userDetails.isAccountNonLocked());
        Assertions.assertTrue(userDetails.isCredentialsNonExpired());
        Assertions.assertTrue(userDetails.isEnabled());
        Assertions.assertFalse(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void testUserDetailsServiceUsernameNotFound() {
        UsernameNotFoundException usernameNotFoundException = Assertions.assertThrows(UsernameNotFoundException.class, () -> applicationConfig.userDetailsService().loadUserByUsername("username"));
        Assertions.assertEquals("User not found", usernameNotFoundException.getMessage());
    }

    @Test
    void testAuthenticationProvider() {
        Assertions.assertInstanceOf(AuthenticationProvider.class, applicationConfig.authenticationProvider());
    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration config = Mockito.mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);

        Mockito.when(config.getAuthenticationManager()).thenReturn(authenticationManager);
        Assertions.assertEquals(authenticationManager, applicationConfig.authenticationManager(config));
    }

    @Test
    void testPasswordEncoder() {
        Assertions.assertInstanceOf(BCryptPasswordEncoder.class, applicationConfig.passwordEncoder());
    }

}
