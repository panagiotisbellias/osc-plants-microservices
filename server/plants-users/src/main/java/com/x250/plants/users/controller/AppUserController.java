package com.x250.plants.users.controller;

import com.x250.plants.users.dto.AppUserCreateDTO;
import com.x250.plants.users.dto.AppUserResponseDTO;
import com.x250.plants.users.exception.EntityNotFoundException;
import com.x250.plants.users.service.AppUserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<AppUserResponseDTO> getAllUsers() {
        log.debug("getAllUsers()");
        return appUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppUserResponseDTO getUserById(
            @PathVariable String id
    ) throws EntityNotFoundException {
        log.debug("getUserById({})", id);
        return appUserService.getUserById(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserResponseDTO addUser(@RequestBody AppUserCreateDTO appUserCreateDTO) {
        log.debug("addUser({})", appUserCreateDTO.toString());
        return appUserService.addUser(appUserCreateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = "users_plant", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "users_plant")
    @Retry(name = "users_plant")
    public CompletableFuture<String> deleteUser(@PathVariable String id) {
        log.debug("deleteUser({})", id);
        return CompletableFuture.supplyAsync(() -> appUserService.deleteUser(id));
    }

    public CompletableFuture<String> fallbackMethod(String id, RuntimeException ex) {
        log.debug("fallbackMethod({}, {})", id, ex.toString());
        if (ex.getMessage().equals("User " + id + " not found in database")) {
            log.warn(ex.getMessage());
            return CompletableFuture.supplyAsync(ex::getMessage);
        }
        log.error("Oops! Something went wrong, please try to delete user later!");
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please try to delete user later!");
    }

}
