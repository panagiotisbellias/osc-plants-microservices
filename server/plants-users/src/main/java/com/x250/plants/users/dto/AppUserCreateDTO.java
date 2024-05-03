package com.x250.plants.users.dto;

import com.x250.plants.users.model.Role;

public record AppUserCreateDTO(
        String userName,
        String email,
        String password,
        Role role
) {

}
