package com.x250.plants.owners.repository;

import com.x250.plants.owners.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, String> {
}
