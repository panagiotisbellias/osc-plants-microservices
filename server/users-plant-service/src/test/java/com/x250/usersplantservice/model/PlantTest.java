package com.x250.usersplantservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlantTest {

    @Test
    void testNoArgsConstructor() {
        Assertions.assertInstanceOf(Plant.class, new Plant());
    }

    @Test
    void testAllArgsConstructor() {
        Plant plant = new Plant("id", "name", "description", "photo", 0);
        Assertions.assertEquals("id", plant.getId());
        Assertions.assertEquals("name", plant.getName());
        Assertions.assertEquals("description", plant.getDescription());
        Assertions.assertEquals("photo", plant.getPhoto());
        Assertions.assertEquals(0, plant.getWateringInterval());
    }

    @Test
    void testSetters() {
        Plant plant = new Plant();
        plant.setId("id");
        plant.setName("name");
        plant.setDescription("description");
        plant.setPhoto("photo");
        plant.setWateringInterval(0);

        Assertions.assertEquals("id", plant.getId());
        Assertions.assertEquals("name", plant.getName());
        Assertions.assertEquals("description", plant.getDescription());
        Assertions.assertEquals("photo", plant.getPhoto());
        Assertions.assertEquals(0, plant.getWateringInterval());
    }

}
