package com.x250.notificationservice.repository;

import com.x250.notificationservice.model.UsersPlantToWater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<UsersPlantToWater, Long> {

    List<UsersPlantToWater> findByAppUserIdAndReceivedByUserFalse(String id);

    void removeByReceivedByUserIsTrueOrNotificationDateBefore(LocalDateTime data);

    List<UsersPlantToWater> findByEmailSentIsFalseAndReceivedByUserIsFalse();

}
