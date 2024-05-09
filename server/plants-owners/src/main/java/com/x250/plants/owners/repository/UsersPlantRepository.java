package com.x250.plants.owners.repository;

import com.x250.plants.owners.model.UsersPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UsersPlantRepository extends JpaRepository<UsersPlant, Long> {

    List<UsersPlant> findByNotificationDateIsBefore(LocalDateTime currentTime);

    List<UsersPlant> findByAppUserId(String id);

}
