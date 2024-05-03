package com.x250.plants.users.dto;

import com.x250.plants.users.model.Role;

public record AppUserResponseDTO(
        String id,
        String username,
        String email,
        String imageUrl,
        Role role
) {
}