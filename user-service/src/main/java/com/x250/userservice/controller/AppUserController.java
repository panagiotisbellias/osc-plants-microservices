package com.x250.userservice.controller;

import com.x250.userservice.dto.AppUserCreateDTO;
import com.x250.userservice.dto.AppUserResponseDTO;
import com.x250.userservice.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<AppUserResponseDTO> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserResponseDTO addUser(@RequestBody AppUserCreateDTO appUserCreateDTO) {
        return appUserService.addUser(appUserCreateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String id) {
        appUserService.deleteUser(id);
    }

}
