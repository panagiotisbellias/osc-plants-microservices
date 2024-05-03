package com.x250.plants.owners.dto;

import com.x250.plants.owners.model.AppUser;
import com.x250.plants.owners.model.UsersPlant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsersPlantDTOMapperTest {

    @InjectMocks
    UsersPlantDTOMapper usersPlantDTOMapper;

    @Test
    void testApply() {
        UsersPlant usersPlant = Mockito.mock(UsersPlant.class);
        AppUser appUser = Mockito.mock(AppUser.class);

        Mockito.when(usersPlant.getAppUser()).thenReturn(appUser);
        Assertions.assertInstanceOf(UsersPlantResponseDTO.class, usersPlantDTOMapper.apply(usersPlant));
    }

}
