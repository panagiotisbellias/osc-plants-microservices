package com.x250.plants.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x250.plants.chat.model.Message;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    public String stringifyMessage(Message message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
