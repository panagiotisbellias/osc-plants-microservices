package com.x250.chatservice.service;

import com.x250.chatservice.model.Message;
import com.x250.chatservice.repository.MessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    MessageService messageService;

    @Mock
    MessageRepository messageRepository;

    @Test
    void testConstructor() {
        MessageService messageService = new MessageService(messageRepository);
        Assertions.assertInstanceOf(MessageService.class, messageService);
    }

    @Test
    void testGetMessages() {
        Assertions.assertTrue(messageService.getMessages("room").isEmpty());
    }

    @Test
    void testSaveMessage() {
        Message message = Mockito.mock(Message.class);
        Assertions.assertNull(messageService.saveMessage(message));
    }

}
