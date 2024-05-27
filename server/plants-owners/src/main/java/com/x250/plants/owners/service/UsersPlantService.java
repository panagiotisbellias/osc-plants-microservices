package com.x250.plants.owners.service;

import com.x250.plants.owners.dto.UsersPlantCreateDTO;
import com.x250.plants.owners.dto.UsersPlantDTOMapper;
import com.x250.plants.owners.dto.UsersPlantResponseDTO;
import com.x250.plants.owners.model.AppUser;
import com.x250.plants.owners.model.Plant;
import com.x250.plants.owners.model.UsersPlant;
import com.x250.plants.owners.repository.AppUserRepository;
import com.x250.plants.owners.utils.ObjectProvider;
import com.x250.plants.owners.exception.EntityNotFoundException;
import com.x250.plants.owners.repository.PlantRepository;
import com.x250.plants.owners.repository.UsersPlantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersPlantService {

    private final UsersPlantRepository usersPlantRepository;
    private final AppUserRepository appUserRepository;
    private final PlantRepository plantRepository;
    private final UsersPlantDTOMapper usersPlantDTOMapper;

    public UsersPlantResponseDTO addUsersPlant(UsersPlantCreateDTO usersPlantCreateDTO) throws EntityNotFoundException {
        log.debug("addUsersPlant({})", usersPlantCreateDTO.getClass());
        AppUser appUser = ObjectProvider.getObjectFromDB(usersPlantCreateDTO.appUserId(), appUserRepository);
        Plant plant = ObjectProvider.getObjectFromDB(usersPlantCreateDTO.plantId(), plantRepository);
        log.info("App user is {} and plant is {}", appUser, plant);
        UsersPlant usersPlant = UsersPlant.builder()
                .appUser(appUser)
                .plant(plant)
                .nextWatering(LocalDateTime.now().plusDays(plant.getWateringInterval()))
                .notificationDate(LocalDateTime.now().plusDays(plant.getWateringInterval())) // added notifications date
                .build();
        log.info("Users plant is created: {}", usersPlant);
        return usersPlantDTOMapper.apply(usersPlantRepository.save(usersPlant));
    }

    public void deleteUsersPlant(Long id) throws EntityNotFoundException {
        log.debug("deleteUsersPlant({})", id);
        UsersPlant usersPlant = getUsersPlant(id);
        usersPlantRepository.delete(usersPlant);
        log.info("Users plant {} is deleted", usersPlant);
    }

    public void deleteAllUsersPlant(String id) {
        log.debug("deleteAllUsersPlant({})", id);
        List<UsersPlant> usersPlantList = usersPlantRepository.findByAppUserId(id);
        log.info("Users plants: {}", usersPlantList.toArray());
        usersPlantRepository.deleteAllInBatch(usersPlantList);
        log.info("Users plants for user {} are deleted", id);
    }

    public UsersPlantResponseDTO updateNextWatering(Long id) throws EntityNotFoundException {
        log.debug("updateNextWatering({})", id);
        UsersPlant usersPlant = getUsersPlant(id);
        Plant plant = ObjectProvider.getObjectFromDB(usersPlant.getPlant().getId(), plantRepository);
        log.info("Plant retrieved: {}", plant);
        usersPlant.setNextWatering(LocalDateTime.now().plusDays(plant.getWateringInterval()));
        usersPlant.setNotificationDate(LocalDateTime.now().plusDays(plant.getWateringInterval()));
        log.info("Users plant updated: {}", usersPlant);
        return usersPlantDTOMapper.apply(usersPlantRepository.save(usersPlant));
    }

    private UsersPlant getUsersPlant(Long id) throws EntityNotFoundException {
        log.debug("getUsersPlant({})", id);
        UsersPlant usersPlant = ObjectProvider.getObjectFromDB(id, usersPlantRepository);
        log.info("Users plant retrieved: {}", usersPlant);
        return usersPlant;
    }

    public List<UsersPlantResponseDTO> getUsersPlants(String userId) {
        log.debug("getUsersPlants({})", userId);
        List<UsersPlant> usersPlants = usersPlantRepository.findByAppUserId(userId);
        log.info("Users plants retrieved: {}", usersPlants.toArray());
        return usersPlants.stream()
                .map(usersPlantDTOMapper)
                .toList();
    }
}
