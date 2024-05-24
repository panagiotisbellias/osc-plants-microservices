package com.x250.plants.api.gateway.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final Log logger = LogFactory.getLog(SecurityConfiguration.class);

    private final AuthFilter authFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        logger.debug(String.format("springSecurityFilterChain(%s)", serverHttpSecurity.getClass()));
        serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(Customizer.withDefaults())
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchange ->
                        exchange
                                .pathMatchers(HttpMethod.OPTIONS)
                                .permitAll()
                                .pathMatchers(
                                        "/eureka/**",
                                        "/api/v1/auth/**",
                                        "/stomp/**",

                                        "/auth/**",
                                        "/oauth2/**"

                                )
                                .permitAll()
//                                .pathMatchers(GET, "/api/plant/")
//                                .permitAll()
//                                .pathMatchers(DELETE, "/api/users_plant/user/**")
//                                .permitAll()
                                .anyExchange()
                                .authenticated())
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
//
        ;
        logger.info("Server http security object is constructed");
        return serverHttpSecurity.build();
    }

}