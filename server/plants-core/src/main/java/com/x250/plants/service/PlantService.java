package com.x250.plants.service;

import com.x250.plants.dto.PlantCreateDTO;
import com.x250.plants.dto.PlantDTOMapper;
import com.x250.plants.dto.PlantResponseDTO;
import com.x250.plants.exception.EntityNotFoundException;
import com.x250.plants.model.Plant;
import com.x250.plants.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantDTOMapper plantDTOMapper;

    public List<Plant> getAllPlants() {
        log.debug("getAllPlants()");
        return plantRepository.findAll();
    }

    public PlantResponseDTO addPlant(PlantCreateDTO plantCreateDTO) {
        log.debug("addPlant({})", plantCreateDTO.toString());
        Plant plant = mapPlantCreateDTO(plantCreateDTO);
        log.info("Plant {} created", plant);
        return plantDTOMapper.apply(plantRepository.save(plant));
    }

    private Plant mapPlantCreateDTO(PlantCreateDTO plantCreateDTO) {
        log.debug("mapPlantCreateDTO({})", plantCreateDTO);
        return Plant.builder()
                .name(plantCreateDTO.name())
                .description(plantCreateDTO.description())
                .photo(plantCreateDTO.photo())
                .wateringInterval(plantCreateDTO.wateringInterval())
                .build();
    }

    public List<PlantResponseDTO> searchPlantsByName(String name) {
        log.debug("searchPlantsByName({})", name);
        return plantRepository.findByNameContainsIgnoreCase(name).stream()
                .map(plantDTOMapper)
                .toList();
    }

    public PlantResponseDTO getPlantById(String id) throws EntityNotFoundException {
        log.debug("getPlantById({})", id);
        return plantDTOMapper.apply(plantRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plant " + id + " not found in DB")));
    }

}
