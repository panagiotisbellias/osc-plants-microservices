package com.x250.plants.users.dto;

import com.x250.plants.users.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppUserResponseDTOTest {

    @Test
    void testAppUserResponseDTO() {
        Role role = Mockito.mock(Role.class);
        Mockito.when(role.name()).thenReturn("role");

        AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO("id", "username", "email", "image url", role);
        Assertions.assertEquals("id", appUserResponseDTO.id());
        Assertions.assertEquals("username", appUserResponseDTO.username());
        Assertions.assertEquals("email", appUserResponseDTO.email());
        Assertions.assertEquals("image url", appUserResponseDTO.imageUrl());
        Assertions.assertEquals("role", appUserResponseDTO.role().name());
    }

}
