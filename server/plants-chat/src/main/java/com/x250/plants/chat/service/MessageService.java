package com.x250.plants.chat.service;

import com.x250.plants.chat.model.Message;
import com.x250.plants.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    public List<Message> getMessages(String room) {
        log.debug("getMessages({})", room);
        return messageRepository.findAllByRoom(room);
    }

    public Message saveMessage(Message message) {
        log.debug("saveMessage({})", message.toString());
        return messageRepository.save(message);
    }

}