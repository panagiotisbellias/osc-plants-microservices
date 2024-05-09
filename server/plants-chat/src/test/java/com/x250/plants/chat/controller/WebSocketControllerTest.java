package com.x250.plants.chat.controller;

import com.x250.plants.chat.model.Message;
import com.x250.plants.chat.service.MessageService;
import com.x250.plants.chat.service.WebSocketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WebSocketControllerTest {

    @InjectMocks
    WebSocketController webSocketController;

    @Mock
    WebSocketService webSocketService;

    @Mock
    MessageService messageService;

    @Test
    void testConstructor() {
        WebSocketController webSocketController = new WebSocketController(webSocketService, messageService);
        Assertions.assertInstanceOf(WebSocketController.class, webSocketController);
    }

    @Test
    void testProcessMessageFromClient() {
        Message message = Mockito.mock(Message.class);
        Assertions.assertNull(webSocketController.processMessageFromClient(message));
    }

}
