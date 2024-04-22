package com.x250.chatservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
class MessageTest {

    @Mock
    Date date;

    @Test
    void testNoArgsConstructor() {
        Message message = new Message();
        Assertions.assertInstanceOf(Message.class, message);
    }

    @Test
    void testAllArgsConstructor() {
        Message message = new Message(0L, "room", "username", "email", "image url", "message", date);
        Assertions.assertEquals(0L, message.getId());
        Assertions.assertEquals("room", message.getRoom());
        Assertions.assertEquals("username", message.getUsername());
        Assertions.assertEquals("email", message.getEmail());
        Assertions.assertEquals("image url", message.getImageUrl());
        Assertions.assertEquals("message", message.getMessage());
        Assertions.assertInstanceOf(Date.class, message.getCreatedDateTime());
    }

    @Test
    void testSetters() {
        Message message = new Message();
        message.setId(0L);
        message.setRoom("room");
        message.setUsername("username");
        message.setEmail("email");
        message.setImageUrl("image url");
        message.setMessage("message");
        message.setCreatedDateTime(date);

        Assertions.assertEquals(0L, message.getId());
        Assertions.assertEquals("room", message.getRoom());
        Assertions.assertEquals("username", message.getUsername());
        Assertions.assertEquals("email", message.getEmail());
        Assertions.assertEquals("image url", message.getImageUrl());
        Assertions.assertEquals("message", message.getMessage());
        Assertions.assertInstanceOf(Date.class, message.getCreatedDateTime());
    }

}
