package com.x250.plants.api.gateway.config;

import com.x250.plants.api.gateway.model.AppUser;
import com.x250.plants.api.gateway.model.UserPrincipal;
import com.x250.plants.api.gateway.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private static final Log logger = LogFactory.getLog(ApplicationConfig.class);

    private final AppUserRepository appUserRepository;

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        logger.debug("reactiveUserDetailsService()");
        return username -> { // anonymous inner class
            AppUser appUser = appUserRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            logger.info(String.format("App user %s exists", username));
            UserDetails userDetails = UserPrincipal.create(appUser);
            return Mono.just(userDetails);
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        logger.debug("userDetailsService()");
        return username -> { // anonymous inner class
            AppUser appUser = appUserRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            logger.info(String.format("App user %s exists", username));
            return UserPrincipal.create(appUser);
        };
    } //This is a lambda expression that serves as the implementation of the loadUserByUsername method in the UserDetailsService interface

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                                                       PasswordEncoder passwordEncoder) {
        logger.debug(String.format("reactiveAuthenticationManager(%s, %s)", userDetailsService.getClass(), passwordEncoder.getClass()));
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("passwordEncoder()");
        return new BCryptPasswordEncoder();
    }

}
