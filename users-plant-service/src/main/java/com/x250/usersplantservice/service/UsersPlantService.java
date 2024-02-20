package com.x250.usersplantservice.service;

import com.x250.usersplantservice.dto.UsersPlantCreateDTO;
import com.x250.usersplantservice.dto.UsersPlantDTOMapper;
import com.x250.usersplantservice.dto.UsersPlantResponseDTO;
import com.x250.usersplantservice.exception.EntityNotFoundException;
import com.x250.usersplantservice.model.AppUser;
import com.x250.usersplantservice.model.Plant;
import com.x250.usersplantservice.model.UsersPlant;
import com.x250.usersplantservice.repository.AppUserRepository;
import com.x250.usersplantservice.repository.PlantRepository;
import com.x250.usersplantservice.repository.UsersPlantRepository;
import com.x250.usersplantservice.utils.ObjectProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
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
                .build();

        return usersPlantDTOMapper.apply(usersPlantRepository.save(usersPlant));
    }

    public void deleteUsersPlant(Long id) throws EntityNotFoundException {
        UsersPlant usersPlant = ObjectProvider.getObjectFromDB(id, usersPlantRepository);

        usersPlantRepository.delete(usersPlant);
    }


    public UsersPlantResponseDTO updateNextWatering(Long id) throws EntityNotFoundException {
        UsersPlant usersPlant = ObjectProvider.getObjectFromDB(id, usersPlantRepository);

        Plant plant = ObjectProvider.getObjectFromDB(usersPlant.getPlant().getId(), plantRepository);

        usersPlant.setNextWatering(LocalDateTime.now().plusDays(plant.getWateringInterval()));

        return usersPlantDTOMapper.apply(usersPlantRepository.save(usersPlant));
    }
}