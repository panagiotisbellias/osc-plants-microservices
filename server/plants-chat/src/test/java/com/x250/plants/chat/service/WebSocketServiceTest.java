package com.x250.plants.chat.service;

import com.x250.plants.chat.model.Message;
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
        Assertions.assertEquals("{\"id\":0,\"room\":null,\"username\":null,\"email\":null,\"imageUrl\":null,\"content\":null,\"createdDateTime\":null}", webSocketService.stringifyMessage(message));
    }

}
