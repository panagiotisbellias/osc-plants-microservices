package com.x250.plants.owners.controller;

import com.x250.plants.owners.dto.UsersPlantCreateDTO;
import com.x250.plants.owners.dto.UsersPlantResponseDTO;
import com.x250.plants.owners.exception.EntityNotFoundException;
import com.x250.plants.owners.service.UsersPlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users_plant")
@RequiredArgsConstructor
public class UsersPlantController {

    private final UsersPlantService usersPlantService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public UsersPlantResponseDTO addUsersPlant(@RequestBody UsersPlantCreateDTO usersPlantCreateDTO) throws EntityNotFoundException {
        log.debug("addUsersPlant({})", usersPlantCreateDTO.getClass());
        return usersPlantService.addUsersPlant(usersPlantCreateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUsersPlant(
            @PathVariable Long id
    ) throws EntityNotFoundException {
        log.debug("deleteUsersPlant({})", id);
        usersPlantService.deleteUsersPlant(id);
        log.info("User plant {} is deleted", id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
        public List<UsersPlantResponseDTO> getUsersPlants(
                @PathVariable String id) {
        log.debug("getUsersPlants({})", id);
        return usersPlantService.getUsersPlants(id);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteAllUsersPlants(
            @PathVariable String id
    ) {
        log.debug("deleteAllUsersPlants({})", id);
        usersPlantService.deleteAllUsersPlant(id);
        log.info("All plants of user {} are deleted", id);
        return true;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsersPlantResponseDTO updateNextWatering(@PathVariable Long id) throws EntityNotFoundException {
        log.debug("updateNextWatering({})", id);
        return usersPlantService.updateNextWatering(id);
    }

}
