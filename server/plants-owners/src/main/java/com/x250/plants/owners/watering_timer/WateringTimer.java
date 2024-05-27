package com.x250.plants.owners.watering_timer;

import com.x250.plants.owners.event.PlantWateringEvent;
import com.x250.plants.owners.model.UsersPlant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableKafka
public class WateringTimer {

    private final WateringChecker wateringChecker;

    private final KafkaTemplate<String, PlantWateringEvent> kafkaTemplate;

    @Scheduled(fixedDelay = 10000) // 1000 milliseconds (1 second)
    public void notifyAboutPlantsToWater() {
        log.debug("notifyAboutPlantsToWater()");
        LocalDateTime currentTime = LocalDateTime.now();
        log.info("Current time: {}", currentTime);
        List<UsersPlant> plantsToWater = wateringChecker.findPlantsToWater(currentTime);
        log.info("Plants to water: {}", plantsToWater.toArray());
        plantsToWater.forEach(usersPlant -> {
            log.info("Users plant: {}", usersPlant.toString());
            PlantWateringEvent plantWateringEvent = new PlantWateringEvent(
                    usersPlant.getId(),
                    usersPlant.getPlant().getName(),
                    LocalDateTime.now(),
                    usersPlant.getAppUser().getId(),
                    usersPlant.getAppUser().getEmail()
            );
            log.info("Plant watering event: {}", plantWateringEvent);
            try {
                kafkaTemplate.send("notificationTopic", plantWateringEvent).get(100, TimeUnit.MILLISECONDS);
                log.info("!---------Notification sent to Kafka----------!");
                wateringChecker.moveNextWateringOneDayAhead(usersPlant);
            } catch (ExecutionException e) {
                log.error("Kafka send message failure 1 !");
                throw new RuntimeException("Kafka failure 1 !");
            } catch (TimeoutException e) {
                log.error("Kafka send message failure 2 !");
                throw new RuntimeException("Kafka failure 2 !");
            } catch (InterruptedException e) {
                log.error("Kafka send message failure 3 !");
                throw new RuntimeException("Kafka failure 3 !");
            } catch (java.util.concurrent.TimeoutException e) {
                log.error("Kafka send message failure 4 !");
                throw new RuntimeException("Kafka failure 4 !");

            }
        });

    }

}
