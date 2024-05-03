package com.x250.plants.owners.service;

import com.x250.plants.owners.dto.UsersPlantCreateDTO;
import com.x250.plants.owners.dto.UsersPlantDTOMapper;
import com.x250.plants.owners.exception.EntityNotFoundException;
import com.x250.plants.owners.model.AppUser;
import com.x250.plants.owners.model.Plant;
import com.x250.plants.owners.model.UsersPlant;
import com.x250.plants.owners.repository.AppUserRepository;
import com.x250.plants.owners.repository.PlantRepository;
import com.x250.plants.owners.repository.UsersPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsersPlantServiceTest {

    @InjectMocks
    UsersPlantService usersPlantService;

    @Mock
    UsersPlantRepository usersPlantRepository;

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    PlantRepository plantRepository;

    @Mock
    UsersPlantDTOMapper usersPlantDTOMapper;

    @Mock
    UsersPlant usersPlant;

    @Test
    void testConstructor() {
        UsersPlantService usersPlantService = new UsersPlantService(usersPlantRepository, appUserRepository, plantRepository, usersPlantDTOMapper);
        Assertions.assertInstanceOf(UsersPlantService.class, usersPlantService);
    }

    @Test
    void testAddUsersPlant() throws EntityNotFoundException {
        UsersPlantCreateDTO usersPlantCreateDTO = Mockito.mock(UsersPlantCreateDTO.class);
        AppUser appUser = Mockito.mock(AppUser.class);
        Plant plant = Mockito.mock(Plant.class);

        Mockito.when(usersPlantCreateDTO.appUserId()).thenReturn("app user id");
        Mockito.when(usersPlantCreateDTO.plantId()).thenReturn("plant id");
        Mockito.when(appUserRepository.findById("app user id")).thenReturn(Optional.of(appUser));
        Mockito.when(plantRepository.findById("plant id")).thenReturn(Optional.of(plant));
        Assertions.assertNull(usersPlantService.addUsersPlant(usersPlantCreateDTO));
    }

    @Test
    void testDeleteUsersPlant() throws EntityNotFoundException {
        Mockito.when(usersPlantRepository.findById(0L)).thenReturn(Optional.of(usersPlant));
        usersPlantService.deleteUsersPlant(0L);

        Mockito.verify(usersPlantRepository).findById(0L);
        Mockito.verify(usersPlantRepository).delete(usersPlant);
    }

    @Test
    void testDeleteAllUsersPlant() {
        usersPlantService.deleteAllUsersPlant("id");
        Mockito.verify(usersPlantRepository).findByAppUserId("id");
        Mockito.verify(usersPlantRepository).deleteAllInBatch(ArgumentMatchers.anyList());
    }

    @Test
    void testUpdateNextWatering() throws EntityNotFoundException {
        Plant plant = Mockito.mock(Plant.class);
        Mockito.when(plant.getId()).thenReturn("id");
        Mockito.when(usersPlant.getPlant()).thenReturn(plant);
        Mockito.when(usersPlantRepository.findById(0L)).thenReturn(Optional.of(usersPlant));
        Mockito.when(plantRepository.findById("id")).thenReturn(Optional.of(plant));
        Assertions.assertNull(usersPlantService.updateNextWatering(0L));
    }

    @Test
    void testGetUsersPlants() {
        Assertions.assertTrue(usersPlantService.getUsersPlants("user id").isEmpty());
    }

}
