package com.x250.plants.users.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
class WebClientConfigTest {

    @InjectMocks
    WebClientConfig webClientConfig;

    @Test
    void testWebClientBuilder() {
        Assertions.assertInstanceOf(WebClient.Builder.class, webClientConfig.webClientBuilder());
    }

}
