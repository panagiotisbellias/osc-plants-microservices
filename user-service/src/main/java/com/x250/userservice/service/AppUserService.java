package com.x250.userservice.service;

import com.x250.userservice.dto.AppUserCreateDTO;
import com.x250.userservice.dto.AppUserDTOMapper;
import com.x250.userservice.dto.AppUserResponseDTO;
import com.x250.userservice.model.AppUser;
import com.x250.userservice.repository.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserDTOMapper appUserDTOMapper;

    public List<AppUserResponseDTO> getAllUsers() {

        return appUserRepository.findAll().stream()
                .map(appUserDTOMapper)
                .toList();
    }
    public AppUserResponseDTO addUser(AppUserCreateDTO appUserCreateDTO) {
        AppUser appUser = mapToAppUser(appUserCreateDTO);

        return  appUserDTOMapper.apply(appUserRepository.save(appUser));
    }

    public void deleteUser(String id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));

        appUserRepository.delete(appUser);
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
