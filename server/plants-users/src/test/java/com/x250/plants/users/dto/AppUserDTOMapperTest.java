package com.x250.plants.users.dto;

import com.x250.plants.users.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppUserDTOMapperTest {

    @InjectMocks
    AppUserDTOMapper appUserDTOMapper;

    @Test
    void testApply() {
        AppUser appUser = Mockito.mock(AppUser.class);
        Assertions.assertInstanceOf(AppUserResponseDTO.class, appUserDTOMapper.apply(appUser));
    }

}
