package com.x250.plants.owners.dto;

import com.x250.plants.owners.model.UsersPlant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Slf4j
@Service
public class UsersPlantDTOMapper implements Function<UsersPlant, UsersPlantResponseDTO> {


    @Override
    public UsersPlantResponseDTO apply(UsersPlant usersPlant) {
        log.debug("apply({})", usersPlant.toString());
        return new UsersPlantResponseDTO(
                usersPlant.getId(),
                usersPlant.getAppUser().getId(),
                usersPlant.getPlant(),
                usersPlant.getNextWatering()
        );
    }
}
