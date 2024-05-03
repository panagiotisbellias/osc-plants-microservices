package com.x250.userservice.dto;

import com.x250.userservice.model.Role;

public record AppUserCreateDTO(
        String userName,
        String email,
        String password,
        Role role
) {

}
