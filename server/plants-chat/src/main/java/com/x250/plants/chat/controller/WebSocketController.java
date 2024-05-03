package com.x250.plants.chat.controller;


import com.x250.plants.chat.model.Message;
import com.x250.plants.chat.service.MessageService;
import com.x250.plants.chat.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketService webSocketService;
    private final MessageService messageService;

    @MessageMapping("/messages/{room}")
    @SendTo("/topic/messages/{room}")
    public String processMessageFromClient(@RequestBody Message message) {
        Message savedMessage = messageService.saveMessage(message);
        return webSocketService.stringifyMessage(savedMessage);
    }

}
