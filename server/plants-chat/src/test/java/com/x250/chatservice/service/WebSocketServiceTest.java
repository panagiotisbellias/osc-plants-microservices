package com.x250.chatservice.service;

import com.x250.chatservice.model.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WebSocketServiceTest {

    @InjectMocks
    WebSocketService webSocketService;

    @Test
    void testStringifyMessage() {
        Message message = Mockito.mock(Message.class);
        Assertions.assertEquals("{\"id\":0,\"room\":null,\"username\":null,\"email\":null,\"imageUrl\":null,\"message\":null,\"createdDateTime\":null}", webSocketService.stringifyMessage(message));
    }

}