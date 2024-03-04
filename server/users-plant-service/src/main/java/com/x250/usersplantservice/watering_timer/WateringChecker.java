package com.x250.usersplantservice.watering_timer;

import com.x250.usersplantservice.model.UsersPlant;
import com.x250.usersplantservice.repository.UsersPlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WateringChecker {

    private final UsersPlantRepository usersPlantRepository;

    List<UsersPlant> findPlantsToWater(LocalDateTime currentTime) {

        return usersPlantRepository.findByNextWateringIsBefore(currentTime);
    }

    void moveNextWateringOneDayAhead(UsersPlant usersPlant) {
        usersPlant.setNextWatering(LocalDateTime.now().plusDays(1L));
        usersPlantRepository.save(usersPlant);
    }

}
