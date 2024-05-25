package com.x250.plants.authentication.security.config;

import com.x250.plants.authentication.model.AppUser;
import com.x250.plants.authentication.repository.AppUserRepository;
import com.x250.plants.authentication.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AppUserRepository appUserRepository;


    @Bean
    public UserDetailsService userDetailsService() {
        log.debug("userDetailsService()");
        return username -> { // anonymous inner class
            AppUser appUser = appUserRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            log.info("App user {} found", appUser.getUsername());
            return UserPrincipal.create(appUser);
        };
    } //This is a lambda expression that serves as the implementation of the loadUserByUsername method in the UserDetailsService interface


    @Bean // data access object which is responsible to fetch the user details and also encode password and so forth
    public AuthenticationProvider authenticationProvider() {
        log.debug("authenticationProvider()");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // one of implementations
        authProvider.setUserDetailsService(userDetailsService());// we need to tell authentication provider which
        // userDetailService to use in order to fetch information about our user we might have several
        authProvider.setPasswordEncoder(passwordEncoder()); // we need to specify which password encoder we are using in our application
        log.info("Auth provider object instantiated successfully");
        return authProvider;
    }

    @Bean // responsible to manage the authentication. There is a method which allows us to authenticate a user based on using username and password
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.debug("authenticationManager({})", config.getClass());
        log.info("Authentication configuration: {}", config.getAuthenticationManager().getClass());
        return config.getAuthenticationManager(); // we use default implementation of Spring Boot
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("passwordEncoder()");
        return new BCryptPasswordEncoder();
    }



}
