package com.x250.plants.owners.dto;

import com.x250.plants.owners.model.Plant;

import java.time.LocalDateTime;

public record UsersPlantResponseDTO(
        Long id,
        String appUserId,
        Plant plant,
        LocalDateTime nextWatering
) {
}