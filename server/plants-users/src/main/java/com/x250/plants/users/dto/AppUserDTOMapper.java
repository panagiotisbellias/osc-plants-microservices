package com.x250.plants.users.dto;

import com.x250.plants.users.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AppUserDTOMapper implements Function<AppUser, AppUserResponseDTO> {
    @Override
    public AppUserResponseDTO apply(AppUser appUser) {
        return new AppUserResponseDTO(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getEmail(),
                appUser.getImageUrl(),
                appUser.getRole()
        );
    }
}
