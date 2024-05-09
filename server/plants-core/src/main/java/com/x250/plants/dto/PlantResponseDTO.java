package com.x250.plants.dto;

public record PlantResponseDTO(
        String id,
        String name,
        String description,
        String photo,
        Integer wateringInterval
) {
}
