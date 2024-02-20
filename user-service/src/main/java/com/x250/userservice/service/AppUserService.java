package com.x250.userservice.service;

import com.x250.userservice.dto.AppUserCreateDTO;
import com.x250.userservice.dto.AppUserDTOMapper;
import com.x250.userservice.dto.AppUserResponseDTO;
import com.x250.userservice.exception.EntityNotFoundException;
import com.x250.userservice.model.AppUser;
import com.x250.userservice.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserDTOMapper appUserDTOMapper;
    private final RestTemplate restTemplate;

    public List<AppUserResponseDTO> getAllUsers() {

        return appUserRepository.findAll().stream()
                .map(appUserDTOMapper)
                .toList();
    }

    public AppUserResponseDTO addUser(AppUserCreateDTO appUserCreateDTO) {
        AppUser appUser = mapToAppUser(appUserCreateDTO);

        return appUserDTOMapper.apply(appUserRepository.save(appUser));
    }

    public void deleteUser(String id) throws EntityNotFoundException {

        if (deleteAllUsersPlants(id)) {
            AppUser appUser = appUserRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));

            appUserRepository.delete(appUser);
        }
    }

    private boolean deleteAllUsersPlants(String userId) {
        String url = "http://localhost:8080/api/users_plant/user/{userId}";

        // Set headers if needed
        HttpHeaders headers = new HttpHeaders();
        // headers.set("Authorization", "Bearer <your_token>");

        // Create request entity with headers
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Execute HTTP DELETE request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                Void.class,
                userId
        );

        return responseEntity.getStatusCode().is2xxSuccessful();
    }

    private static AppUser mapToAppUser(AppUserCreateDTO appUserCreateDTO) {
        return AppUser.builder()
                .username(appUserCreateDTO.userName())
                .email(appUserCreateDTO.email())
                .password(appUserCreateDTO.password())
                .role(appUserCreateDTO.role())
                .build();
    }

}
