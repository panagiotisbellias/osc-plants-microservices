package com.x250.plants.users.dto;

import com.x250.plants.users.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Slf4j
@Service
public class AppUserDTOMapper implements Function<AppUser, AppUserResponseDTO> {
    @Override
    public AppUserResponseDTO apply(AppUser appUser) {
        log.debug("apply({})", appUser.toString());
        return new AppUserResponseDTO(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getEmail(),
                appUser.getImageUrl(),
                appUser.getRole()
        );
    }
}
