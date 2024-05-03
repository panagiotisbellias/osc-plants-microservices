package com.x250.notificationservice.event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class PlantWateringEventTest {

    @Test
    void testPlantWateringEvent() {
        LocalDateTime notificationDate = Mockito.mock(LocalDateTime.class);
        PlantWateringEvent plantWateringEvent = new PlantWateringEvent(0L, "plant name", notificationDate, "upp user id", "upp user email");

        Assertions.assertEquals(0L, plantWateringEvent.usersPlantId());
        Assertions.assertEquals("plant name", plantWateringEvent.plantName());
        Assertions.assertInstanceOf(LocalDateTime.class, plantWateringEvent.notificationDate());
        Assertions.assertEquals("upp user id", plantWateringEvent.uppUserId());
        Assertions.assertEquals("upp user email", plantWateringEvent.uppUserEmail());
     }

}
