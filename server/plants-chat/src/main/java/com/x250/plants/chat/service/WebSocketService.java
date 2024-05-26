package com.x250.plants.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x250.plants.chat.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class WebSocketService {

    public String stringifyMessage(Message message) {
        log.debug("stringifyMessage({})", message.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }
}
