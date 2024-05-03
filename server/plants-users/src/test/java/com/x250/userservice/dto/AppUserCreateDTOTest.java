package com.x250.userservice.dto;

import com.x250.userservice.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppUserCreateDTOTest {

    @Test
    void testAppUserCreateDTO() {
        Role role = Mockito.mock(Role.class);
        Mockito.when(role.name()).thenReturn("role");

        AppUserCreateDTO appUserCreateDTO = new AppUserCreateDTO("user name", "email", "password", role);
        Assertions.assertEquals("user name", appUserCreateDTO.userName());
        Assertions.assertEquals("email", appUserCreateDTO.email());
        Assertions.assertEquals("password", appUserCreateDTO.password());
        Assertions.assertEquals("role", appUserCreateDTO.role().name());
    }

}
