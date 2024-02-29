package com.x250.notificationservice.message_queue;

import java.time.LocalDateTime;

public record PlantWateringEvent(
        Long usersPlantId,
        String plantName,
        LocalDateTime notificationDate,
        String uppUserId,
        String uppUserEmail
) {
}