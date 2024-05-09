package com.x250.plants.owners.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class UsersPlantTest {

    @Mock
    AppUser appUser;

    @Mock
    Plant plant;

    @Mock
    LocalDateTime notificationDate;

    @Mock
    LocalDateTime nextWatering;

    @Test
    void testAllArgsConstructor() {
        UsersPlant usersPlant = new UsersPlant(0L, appUser, plant, notificationDate, nextWatering);
        Assertions.assertEquals(0L, usersPlant.getId());
        Assertions.assertInstanceOf(AppUser.class, usersPlant.getAppUser());
        Assertions.assertInstanceOf(Plant.class, usersPlant.getPlant());
        Assertions.assertInstanceOf(LocalDateTime.class, usersPlant.getNotificationDate());
        Assertions.assertInstanceOf(LocalDateTime.class, usersPlant.getNextWatering());
    }

    @Test
    void testNoArgsConstructor() {
        Assertions.assertInstanceOf(UsersPlant.class, new UsersPlant());
    }

    @Test
    void testSetters() {
        UsersPlant usersPlant = new UsersPlant();
        usersPlant.setId(0L);
        usersPlant.setAppUser(appUser);
        usersPlant.setPlant(plant);
        usersPlant.setNotificationDate(notificationDate);
        usersPlant.setNextWatering(nextWatering);

        Assertions.assertEquals(0L, usersPlant.getId());
        Assertions.assertInstanceOf(AppUser.class, usersPlant.getAppUser());
        Assertions.assertInstanceOf(Plant.class, usersPlant.getPlant());
        Assertions.assertInstanceOf(LocalDateTime.class, usersPlant.getNotificationDate());
        Assertions.assertInstanceOf(LocalDateTime.class, usersPlant.getNextWatering());
    }

}
