package com.x250.plantservice.service;

import com.x250.plantservice.dto.PlantCreateDTO;
import com.x250.plantservice.dto.PlantDTOMapper;
import com.x250.plantservice.dto.PlantResponseDTO;
import com.x250.plantservice.model.Plant;
import com.x250.plantservice.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantDTOMapper plantDTOMapper;

    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    public PlantResponseDTO addPlant(PlantCreateDTO plantCreateDTO) {
        Plant plant = mapPlantCreateDTO(plantCreateDTO);
        return plantDTOMapper.apply(plantRepository.save(plant));
    }

    private static Plant mapPlantCreateDTO(PlantCreateDTO plantCreateDTO) {
        return Plant.builder()
                .name(plantCreateDTO.name())
                .description(plantCreateDTO.description())
                .photo(plantCreateDTO.photo())
                .wateringInterval(plantCreateDTO.wateringInterval())
                .build();
    }

}
