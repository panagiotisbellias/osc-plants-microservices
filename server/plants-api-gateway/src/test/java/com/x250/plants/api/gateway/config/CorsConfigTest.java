package com.x250.plants.api.gateway.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.cors.reactive.CorsWebFilter;

@ExtendWith(MockitoExtension.class)
class CorsConfigTest {

    @InjectMocks
    CorsConfig corsConfig;

    @Test
    void testCorsWebFilter() {
        Assertions.assertInstanceOf(CorsWebFilter.class, corsConfig.corsWebFilter());
    }

}
