package com.x250.usersplantservice.watering_timer;

import com.x250.usersplantservice.model.UsersPlant;
import com.x250.usersplantservice.repository.UsersPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class WateringCheckerTest {

    @InjectMocks
    WateringChecker wateringChecker;

    @Mock
    UsersPlantRepository usersPlantRepository;

    @Test
    void testConstructor() {
        WateringChecker wateringChecker = new WateringChecker(usersPlantRepository);
        Assertions.assertInstanceOf(WateringChecker.class, wateringChecker);
    }

    @Test
    void testFindPlantsToWater() {
        Assertions.assertTrue(wateringChecker.findPlantsToWater(LocalDateTime.now()).isEmpty());
    }

    @Test
    void testMoveNextWateringOneDayAhead() {
        UsersPlant usersPlant = Mockito.mock(UsersPlant.class);
        wateringChecker.moveNextWateringOneDayAhead(usersPlant);
        Mockito.verify(usersPlantRepository).save(usersPlant);
    }

}
