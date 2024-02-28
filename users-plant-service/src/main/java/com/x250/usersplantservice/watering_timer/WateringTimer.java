package com.x250.usersplantservice.watering_timer;

import com.x250.usersplantservice.event.PlantWateringEvent;
import com.x250.usersplantservice.model.UsersPlant;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WateringTimer {

    private final WateringChecker wateringChecker;
    private final KafkaTemplate<String, PlantWateringEvent> kafkaTemplate;
    private Long counter = 0L;

    @Scheduled(fixedDelay = 3000) // Runs every 1000 milliseconds (1 second)
    public void verifyTime() {
        LocalDateTime currentTime = LocalDateTime.now();

        List<UsersPlant> plantsToWater = wateringChecker.findPlantsToWater(currentTime);
        plantsToWater.forEach(usersPlant -> {
            System.out.println("Plant to water :" + usersPlant.getId());
            kafkaTemplate.send("notificationTopic", new PlantWateringEvent(usersPlant.getId()));
            wateringChecker.moveNextWateringOneDayAhead(usersPlant);
        });



//        System.out.println("notificationTopic" + counter);
//        kafkaTemplate.send("notificationTopic", new PlantWateringEvent(counter));
//        counter++;

    }


}
