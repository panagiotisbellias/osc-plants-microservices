package com.x250.plants.owners.event;

import java.time.LocalDateTime;

public record PlantWateringEvent(
        Long usersPlantId,
        String plantName,
        LocalDateTime notificationDate,
        String uppUserId,
        String uppUserEmail
) {
}


