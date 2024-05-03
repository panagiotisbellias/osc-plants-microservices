package com.x250.plants.notifications.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class UsersPlantToWaterTest {

    @Test
    void testNoArgsConstructor() {
        UsersPlantToWater usersPlantToWater = new UsersPlantToWater();
        Assertions.assertInstanceOf(UsersPlantToWater.class, usersPlantToWater);
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime notificationDate = Mockito.mock(LocalDateTime.class);
        UsersPlantToWater usersPlantToWater = new UsersPlantToWater(0L, "plant name", notificationDate, "app user id", "app user email", true);

        Assertions.assertEquals(0L, usersPlantToWater.getUsersPlantId());
        Assertions.assertEquals("plant name", usersPlantToWater.getPlantName());
        Assertions.assertInstanceOf(LocalDateTime.class, usersPlantToWater.getNotificationDate());
        Assertions.assertEquals("app user id", usersPlantToWater.getAppUserId());
        Assertions.assertEquals("app user email", usersPlantToWater.getAppUserEmail());
        Assertions.assertTrue(usersPlantToWater.getEmailSent());
    }

    @Test
    void testSetters() {
        LocalDateTime notificationDate = Mockito.mock(LocalDateTime.class);
        UsersPlantToWater usersPlantToWater = new UsersPlantToWater();

        usersPlantToWater.setUsersPlantId(0L);
        usersPlantToWater.setPlantName("plant name");
        usersPlantToWater.setNotificationDate(notificationDate);
        usersPlantToWater.setAppUserId("app user id");
        usersPlantToWater.setAppUserEmail("app user email");
        usersPlantToWater.setEmailSent(true);

        Assertions.assertEquals(0L, usersPlantToWater.getUsersPlantId());
        Assertions.assertEquals("plant name", usersPlantToWater.getPlantName());
        Assertions.assertInstanceOf(LocalDateTime.class, usersPlantToWater.getNotificationDate());
        Assertions.assertEquals("app user id", usersPlantToWater.getAppUserId());
        Assertions.assertEquals("app user email", usersPlantToWater.getAppUserEmail());
        Assertions.assertTrue(usersPlantToWater.getEmailSent());
    }

}
