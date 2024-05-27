package com.x250.plants.users.service;

import com.x250.plants.users.dto.AppUserCreateDTO;
import com.x250.plants.users.dto.AppUserDTOMapper;
import com.x250.plants.users.dto.AppUserResponseDTO;
import com.x250.plants.users.model.AppUser;
import com.x250.plants.users.exception.EntityNotFoundException;
import com.x250.plants.users.repository.AppUserRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserDTOMapper appUserDTOMapper;
    private final WebClient.Builder webClientBuilder;
    private final ObservationRegistry observationRegistry;

    public List<AppUserResponseDTO> getAllUsers() {
        log.debug("getAllUsers()");
        return appUserRepository.findAll().stream()
                .map(appUserDTOMapper)
                .toList();
    }

    public AppUserResponseDTO addUser(AppUserCreateDTO appUserCreateDTO) {
        log.debug("addUser({})", appUserCreateDTO.toString());
        AppUser appUser = mapToAppUser(appUserCreateDTO);
        log.info("Mapping to {} is done", appUser);
        return appUserDTOMapper.apply(appUserRepository.save(appUser));
    }

    public String deleteUser(String id) {
        log.debug("deleteUser({})", id);
        Observation inventoryServiceObservation = Observation.createNotStarted("users-plant-service-lookup",
                this.observationRegistry);
        inventoryServiceObservation.lowCardinalityKeyValue("call", "users-plant-service");
        return inventoryServiceObservation.observe(() -> {

            boolean successfullyDeletedUsersPlants = deleteAllUsersPlants(id);

            if (successfullyDeletedUsersPlants) {
                AppUser appUser = appUserRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User " + id + " not found in database"));
                log.info("App user {} found", id);
                appUserRepository.delete(appUser);
                log.info("User deleted successfully");
                return "User deleted successfully";
            }

            log.warn("User {} not found", id);
            return "User " + id + " not found";

        });

    }

    private boolean deleteAllUsersPlants(String userId) {
        log.debug("deleteAllUsersPlants({})", userId);
        final String baseUrl = "http://users-plant-service/api/users_plant/user/";
        final String url = baseUrl + userId;
        log.info("URL: {}", url);
        return Boolean.TRUE.equals(deleteUsersPlants(url).block());
    }

    public Mono<Boolean> deleteUsersPlants(String url) {
        log.debug("deleteUsersPlants({})", url);
        return webClientBuilder.build().delete()
                .uri(url)
//                .headers(headers -> headers.setBearerAuth(jwtToken))
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    private static AppUser mapToAppUser(AppUserCreateDTO appUserCreateDTO) {
        log.debug("mapToAppUser({})", appUserCreateDTO.toString());
        return AppUser.builder()
                .username(appUserCreateDTO.userName())
                .email(appUserCreateDTO.email())
                .password(appUserCreateDTO.password())
                .role(appUserCreateDTO.role())
                .build();
    }

    public AppUserResponseDTO getUserById(String id) throws EntityNotFoundException {
        log.debug("getUserById({})", id);
        return appUserDTOMapper.apply(appUserRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found in DB")));
    }
}
