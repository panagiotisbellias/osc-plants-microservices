package com.x250.plants.owners.watering_timer;

import com.x250.plants.owners.event.PlantWateringEvent;
import com.x250.plants.owners.model.AppUser;
import com.x250.plants.owners.model.Plant;
import com.x250.plants.owners.model.UsersPlant;
import org.apache.kafka.common.errors.TimeoutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
class WateringTimerTest {

    @InjectMocks
    WateringTimer wateringTimer;

    @Mock
    WateringChecker wateringChecker;

    @Mock
    KafkaTemplate<String, PlantWateringEvent> kafkaTemplate;

    @Mock
    CompletableFuture<SendResult<String, PlantWateringEvent>> completableFuture;

    @Mock
    UsersPlant usersPlant;

    @Mock
    Plant plant;

    @Mock
    AppUser appUser;

    @Test
    void testConstructor() {
        WateringTimer wateringTimer = new WateringTimer(wateringChecker, kafkaTemplate);
        Assertions.assertInstanceOf(WateringTimer.class, wateringTimer);
    }

    @Test
    void testNotifyAboutPlantsToWater() throws ExecutionException, InterruptedException, TimeoutException, java.util.concurrent.TimeoutException {
        Mockito.when(usersPlant.getPlant()).thenReturn(plant);
        Mockito.when(usersPlant.getAppUser()).thenReturn(appUser);
        Mockito.when(wateringChecker.findPlantsToWater(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(List.of(usersPlant));
        Mockito.when(kafkaTemplate.send(ArgumentMatchers.eq("notificationTopic"), ArgumentMatchers.any(PlantWateringEvent.class))).thenReturn(completableFuture);

        wateringTimer.notifyAboutPlantsToWater();
        Mockito.verify(wateringChecker).findPlantsToWater(ArgumentMatchers.any(LocalDateTime.class));
        Mockito.verify(usersPlant).getId();
        Mockito.verify(usersPlant).getPlant();
        Mockito.verify(plant).getName();
        Mockito.verify(usersPlant, Mockito.times(2)).getAppUser();
        Mockito.verify(appUser).getId();
        Mockito.verify(appUser).getEmail();
        Mockito.verify(kafkaTemplate).send(ArgumentMatchers.eq("notificationTopic"), ArgumentMatchers.any(PlantWateringEvent.class));
        Mockito.verify(completableFuture).get(100, TimeUnit.MILLISECONDS);
    }

    @Test
    void testNotifyAboutPlantsToWaterExecutionException() throws ExecutionException, InterruptedException, TimeoutException, java.util.concurrent.TimeoutException {
        Mockito.when(usersPlant.getPlant()).thenReturn(plant);
        Mockito.when(usersPlant.getAppUser()).thenReturn(appUser);
        Mockito.when(wateringChecker.findPlantsToWater(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(List.of(usersPlant));
        Mockito.when(completableFuture.get(100, TimeUnit.MILLISECONDS)).thenThrow(ExecutionException.class);
        Mockito.when(kafkaTemplate.send(ArgumentMatchers.eq("notificationTopic"), ArgumentMatchers.any(PlantWateringEvent.class))).thenReturn(completableFuture);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> wateringTimer.notifyAboutPlantsToWater());
        Assertions.assertEquals("Kafka failure 1 !", runtimeException.getMessage());
    }

    @Test
    void testNotifyAboutPlantsToWaterKafkaTimeoutException() throws ExecutionException, InterruptedException, TimeoutException, java.util.concurrent.TimeoutException {
        Mockito.when(usersPlant.getPlant()).thenReturn(plant);
        Mockito.when(usersPlant.getAppUser()).thenReturn(appUser);
        Mockito.when(wateringChecker.findPlantsToWater(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(List.of(usersPlant));
        Mockito.when(completableFuture.get(100, TimeUnit.MILLISECONDS)).thenThrow(TimeoutException.class);
        Mockito.when(kafkaTemplate.send(ArgumentMatchers.eq("notificationTopic"), ArgumentMatchers.any(PlantWateringEvent.class))).thenReturn(completableFuture);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> wateringTimer.notifyAboutPlantsToWater());
        Assertions.assertEquals("Kafka failure 2 !", runtimeException.getMessage());
    }

    @Test
    void testNotifyAboutPlantsToWaterInterruptedException() throws ExecutionException, InterruptedException, TimeoutException, java.util.concurrent.TimeoutException {
        Mockito.when(usersPlant.getPlant()).thenReturn(plant);
        Mockito.when(usersPlant.getAppUser()).thenReturn(appUser);
        Mockito.when(wateringChecker.findPlantsToWater(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(List.of(usersPlant));
        Mockito.when(completableFuture.get(100, TimeUnit.MILLISECONDS)).thenThrow(InterruptedException.class);
        Mockito.when(kafkaTemplate.send(ArgumentMatchers.eq("notificationTopic"), ArgumentMatchers.any(PlantWateringEvent.class))).thenReturn(completableFuture);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> wateringTimer.notifyAboutPlantsToWater());
        Assertions.assertEquals("Kafka failure 3 !", runtimeException.getMessage());
    }

    @Test
    void testNotifyAboutPlantsToWaterTimeoutException() throws ExecutionException, InterruptedException, TimeoutException, java.util.concurrent.TimeoutException {
        Mockito.when(usersPlant.getPlant()).thenReturn(plant);
        Mockito.when(usersPlant.getAppUser()).thenReturn(appUser);
        Mockito.when(wateringChecker.findPlantsToWater(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(List.of(usersPlant));
        Mockito.when(completableFuture.get(100, TimeUnit.MILLISECONDS)).thenThrow(java.util.concurrent.TimeoutException.class);
        Mockito.when(kafkaTemplate.send(ArgumentMatchers.eq("notificationTopic"), ArgumentMatchers.any(PlantWateringEvent.class))).thenReturn(completableFuture);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> wateringTimer.notifyAboutPlantsToWater());
        Assertions.assertEquals("Kafka failure 4 !", runtimeException.getMessage());
    }

}
