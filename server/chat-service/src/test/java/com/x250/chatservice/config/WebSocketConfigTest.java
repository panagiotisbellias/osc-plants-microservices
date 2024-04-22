package com.x250.chatservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

@ExtendWith(MockitoExtension.class)
class WebSocketConfigTest {

    @InjectMocks
    WebSocketConfig webSocketConfig;

    @Test
    void testRegisterStompEndpoints() {
        StompEndpointRegistry registry = Mockito.mock(StompEndpointRegistry.class);
        StompWebSocketEndpointRegistration endpointRegistration = Mockito.mock(StompWebSocketEndpointRegistration.class);

        Mockito.when(registry.addEndpoint("/stomp")).thenReturn(endpointRegistration);
        Mockito.when(endpointRegistration.setAllowedOrigins("http://localhost:5173")).thenReturn(endpointRegistration);
        webSocketConfig.registerStompEndpoints(registry);

        Mockito.verify(registry).addEndpoint("/stomp");
        Mockito.verify(endpointRegistration).setAllowedOrigins("http://localhost:5173");
        Mockito.verify(endpointRegistration).withSockJS();
    }

    @Test
    void testConfigureMessageBroker() {
        MessageBrokerRegistry registry = Mockito.mock(MessageBrokerRegistry.class);
        webSocketConfig.configureMessageBroker(registry);

        Mockito.verify(registry).enableSimpleBroker("/topic");
        Mockito.verify(registry).setApplicationDestinationPrefixes("/app");
    }

}
