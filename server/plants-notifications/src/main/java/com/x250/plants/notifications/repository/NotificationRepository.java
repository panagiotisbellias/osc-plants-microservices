package com.x250.plants.notifications.repository;

import com.x250.plants.notifications.model.UsersPlantToWater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<UsersPlantToWater, Long> {

    void removeByEmailSentIsTrueOrNotificationDateBefore(LocalDateTime data);

    List<UsersPlantToWater> findByEmailSentIsFalse();

}
