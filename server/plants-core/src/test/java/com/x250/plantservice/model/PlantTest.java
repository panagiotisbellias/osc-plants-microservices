package com.x250.plantservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlantTest {

    @InjectMocks
    private Plant plant;

    @Test
    void testNoArgsConstructor() {
        Assertions.assertInstanceOf(Plant.class, new Plant());
    }

    @Test
    void testGetId() {
        String id = "id";
        plant.setId(id);

        String retrievedId = plant.getId();
        Assertions.assertEquals(id, retrievedId);
    }

    @Test
    void testGetName() {
        String name = "name";
        plant.setName(name);

        String retrievedName = plant.getName();
        Assertions.assertEquals(name, retrievedName);
    }

    @Test
    void testGetDescription() {
        String description = "description";
        plant.setDescription(description);

        String retrievedDescription = plant.getDescription();
        Assertions.assertEquals(description, retrievedDescription);
    }

    @Test
    void testGetPhoto() {
        String photo = "photo";
        plant.setPhoto(photo);

        String retrievedPhoto = plant.getPhoto();
        Assertions.assertEquals(photo, retrievedPhoto);
    }

    @Test
    void testGetWateringInterval() {
        Integer wateringInterval = 0;
        plant.setWateringInterval(wateringInterval);

        Integer retrievedWateringInterval = plant.getWateringInterval();
        Assertions.assertEquals(wateringInterval, retrievedWateringInterval);
    }

}