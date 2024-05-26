package com.x250.plants.dto;

import com.x250.plants.model.Plant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Slf4j
@Service
public class PlantDTOMapper implements Function<Plant, PlantResponseDTO> {

    @Override
    public PlantResponseDTO apply(Plant plant) {
        log.debug("apply({})", plant.toString());
        return new PlantResponseDTO(
                plant.getId(),
                plant.getName(),
                plant.getDescription(),
                plant.getPhoto(),
                plant.getWateringInterval()
        );
    }
}
