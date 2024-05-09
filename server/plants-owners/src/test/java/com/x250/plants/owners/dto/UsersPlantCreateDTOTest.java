package com.x250.plants.owners.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsersPlantCreateDTOTest {

    @Test
    void testUsersPlantCreateDTO() {
        UsersPlantCreateDTO usersPlantCreateDTO = new UsersPlantCreateDTO("app user id", "plant id");
        Assertions.assertEquals("app user id", usersPlantCreateDTO.appUserId());
        Assertions.assertEquals("plant id", usersPlantCreateDTO.plantId());
    }

}
