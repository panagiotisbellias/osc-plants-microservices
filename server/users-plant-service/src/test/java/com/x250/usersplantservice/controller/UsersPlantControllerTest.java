package com.x250.usersplantservice.controller;

import com.x250.usersplantservice.dto.UsersPlantCreateDTO;
import com.x250.usersplantservice.exception.EntityNotFoundException;
import com.x250.usersplantservice.service.UsersPlantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsersPlantControllerTest {

    @InjectMocks
    UsersPlantController usersPlantController;

    @Mock
    UsersPlantService usersPlantService;

    @Test
    void testAddUsersPlant() throws EntityNotFoundException {
        UsersPlantCreateDTO usersPlantCreateDTO = Mockito.mock(UsersPlantCreateDTO.class);
        Assertions.assertNull(usersPlantController.addUsersPlant(usersPlantCreateDTO));
    }

    @Test
    void testDeleteUsersPlant() throws EntityNotFoundException {
        usersPlantController.deleteUsersPlant(0L);
        Mockito.verify(usersPlantService).deleteUsersPlant(0L);
    }

    @Test
    void testGetUsersPlants() {
        Assertions.assertTrue(usersPlantController.getUsersPlants("id").isEmpty());
    }

    @Test
    void testDeleteAllUsersPlants() {
        Assertions.assertTrue(usersPlantController.deleteAllUsersPlants("id"));
    }

    @Test
    void testUpdateNextWatering() throws EntityNotFoundException {
        Assertions.assertNull(usersPlantController.updateNextWatering(0L));
    }

}
