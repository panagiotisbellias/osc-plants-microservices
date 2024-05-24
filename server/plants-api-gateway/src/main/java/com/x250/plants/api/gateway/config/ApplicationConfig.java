package com.x250.plants.api.gateway.config;

import com.x250.plants.api.gateway.model.AppUser;
import com.x250.plants.api.gateway.model.UserPrincipal;
import com.x250.plants.api.gateway.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AppUserRepository appUserRepository;

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        log.debug("reactiveUserDetailsService()");
        return username -> { // anonymous inner class
            AppUser appUser = findAppUser(username);
            UserDetails userDetails = UserPrincipal.create(appUser);
            return Mono.just(userDetails);
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        log.debug("userDetailsService()");
        return username -> { // anonymous inner class
            AppUser appUser = findAppUser(username);
            return UserPrincipal.create(appUser);
        };
    } //This is a lambda expression that serves as the implementation of the loadUserByUsername method in the UserDetailsService interface

    private AppUser findAppUser(String username) {
        AppUser appUser = appUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("App user {} exists", username);
        return appUser;
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                                                       PasswordEncoder passwordEncoder) {
        log.debug("reactiveAuthenticationManager({}, {})", userDetailsService.getClass(), passwordEncoder.getClass());
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("passwordEncoder()");
        return new BCryptPasswordEncoder();
    }

}
