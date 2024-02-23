package com.x250.authenticationservice.dto;

import com.x250.authenticationservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record RegisterRequest(
        String username,
        String email,
        String password,
        Role role
) {
}
