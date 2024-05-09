package com.x250.plants.dto;


import com.x250.plants.model.Plant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlantDTOMapperTest {

    @InjectMocks
    PlantDTOMapper plantDTOMapper;

    @Test
    void testApply() {
        Plant plant = Mockito.mock(Plant.class);
        Mockito.when(plant.getId()).thenReturn("id");
        Mockito.when(plant.getName()).thenReturn("name");
        Mockito.when(plant.getDescription()).thenReturn("description");
        Mockito.when(plant.getPhoto()).thenReturn("photo");
        Mockito.when(plant.getWateringInterval()).thenReturn(0);

        PlantResponseDTO plantResponseDTO = plantDTOMapper.apply(plant);
        Assertions.assertInstanceOf(PlantResponseDTO.class, plantResponseDTO);
        Assertions.assertEquals("id", plantResponseDTO.id());
        Assertions.assertEquals("name", plantResponseDTO.name());
        Assertions.assertEquals("description", plantResponseDTO.description());
        Assertions.assertEquals("photo", plantResponseDTO.photo());
        Assertions.assertEquals(0, plantResponseDTO.wateringInterval());
    }

}
