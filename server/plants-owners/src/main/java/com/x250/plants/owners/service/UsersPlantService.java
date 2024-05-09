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

        AppUser appUser = ObjectProvider.getObjectFromDB(usersPlantCreateDTO.appUserId(), appUserRepository);
        Plant plant = ObjectProvider.getObjectFromDB(usersPlantCreateDTO.plantId(), plantRepository);

        UsersPlant usersPlant = UsersPlant.builder()
                .appUser(appUser)
                .plant(plant)
                .nextWatering(LocalDateTime.now().plusDays(plant.getWateringInterval()))
                .notificationDate(LocalDateTime.now().plusDays(plant.getWateringInterval())) // added notifications date
                .build();

        return usersPlantDTOMapper.apply(usersPlantRepository.save(usersPlant));
    }

    public void deleteUsersPlant(Long id) throws EntityNotFoundException {
        UsersPlant usersPlant = ObjectProvider.getObjectFromDB(id, usersPlantRepository);

        usersPlantRepository.delete(usersPlant);
    }

    public void deleteAllUsersPlant(String id) {
        List<UsersPlant> usersPlantList = usersPlantRepository.findByAppUserId(id);

        usersPlantRepository.deleteAllInBatch(usersPlantList);
    }

    public UsersPlantResponseDTO updateNextWatering(Long id) throws EntityNotFoundException {
        UsersPlant usersPlant = ObjectProvider.getObjectFromDB(id, usersPlantRepository);

        Plant plant = ObjectProvider.getObjectFromDB(usersPlant.getPlant().getId(), plantRepository);

        usersPlant.setNextWatering(LocalDateTime.now().plusDays(plant.getWateringInterval()));
        usersPlant.setNotificationDate(LocalDateTime.now().plusDays(plant.getWateringInterval()));

        return usersPlantDTOMapper.apply(usersPlantRepository.save(usersPlant));
    }


    public List<UsersPlantResponseDTO> getUsersPlants(String userId) {

        List<UsersPlant> usersPlants = usersPlantRepository.findByAppUserId(userId);

        return usersPlants.stream()
                .map(usersPlantDTOMapper)
                .toList();
    }
}
