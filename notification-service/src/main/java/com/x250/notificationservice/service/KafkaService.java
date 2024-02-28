package com.x250.notificationservice.service;

import com.x250.notificationservice.event.PlantWateringEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaService {

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(PlantWateringEvent plantWateringEvent) {
        log.info("Received Notification for Plant: {}", plantWateringEvent.UsersPlantId());
    }


}
