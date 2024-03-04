package com.x250.notificationservice.service;

import com.x250.notificationservice.model.UsersPlantToWater;
import com.x250.notificationservice.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public List<Long> getNotifications(String userId) {

        List<UsersPlantToWater> usersPlantsToWater = notificationRepository.findByAppUserIdAndReceivedByUserFalse(userId);
        usersPlantsToWater.forEach(usersPlantToWater -> usersPlantToWater.setReceivedByUser(true));

        return usersPlantsToWater.stream()
                .map(UsersPlantToWater::getUsersPlantId)
                .toList();
    }

}
