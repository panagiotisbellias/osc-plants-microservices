package com.x250.plants.users.service;

import com.x250.plants.users.dto.AppUserCreateDTO;
import com.x250.plants.users.dto.AppUserDTOMapper;
import com.x250.plants.users.exception.EntityNotFoundException;
import com.x250.plants.users.model.AppUser;
import com.x250.plants.users.repository.AppUserRepository;
import io.micrometer.observation.ObservationRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @InjectMocks
    AppUserService appUserService;

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    AppUserDTOMapper appUserDTOMapper;

    @Mock
    WebClient.Builder webClientBuilder;

    @Mock
    ObservationRegistry observationRegistry;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    Mono mono;

    @Test
    void testConstructor() {
        AppUserService appUserService = new AppUserService(appUserRepository, appUserDTOMapper, webClientBuilder, observationRegistry);
        Assertions.assertInstanceOf(AppUserService.class, appUserService);
    }

    @Test
    void testGetAllUsers() {
        Assertions.assertTrue(appUserService.getAllUsers().isEmpty());
    }

    @Test
    void testAddUser() {
        AppUserCreateDTO appUserCreateDTO = Mockito.mock(AppUserCreateDTO.class);
        Assertions.assertNull(appUserService.addUser(appUserCreateDTO));
    }

    @Test
    void testDeleteUser() {
        ObservationRegistry.ObservationConfig observationConfig = Mockito.mock(ObservationRegistry.ObservationConfig.class);
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
        AppUser appUser = Mockito.mock(AppUser.class);

        Mockito.when(observationRegistry.observationConfig()).thenReturn(observationConfig);
        Mockito.when(appUserRepository.findById("id")).thenReturn(Optional.of(appUser));
        Mockito.when(mono.block()).thenReturn(Boolean.TRUE);
        Mockito.when(responseSpec.bodyToMono(Boolean.class)).thenReturn(mono);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(requestHeadersUriSpec.uri("http://users-plant-service/api/users_plant/user/id")).thenReturn(requestHeadersSpec);
        Mockito.when(webClient.delete()).thenReturn(requestHeadersUriSpec);
        Mockito.when(webClientBuilder.build()).thenReturn(webClient);
        Assertions.assertEquals("User deleted successfully", appUserService.deleteUser("id"));
    }

    @Test
    void testDeleteUserNotFound() {
        ObservationRegistry.ObservationConfig observationConfig = Mockito.mock(ObservationRegistry.ObservationConfig.class);
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        Mockito.when(observationRegistry.observationConfig()).thenReturn(observationConfig);
        Mockito.when(responseSpec.bodyToMono(Boolean.class)).thenReturn(mono);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(requestHeadersUriSpec.uri("http://users-plant-service/api/users_plant/user/id")).thenReturn(requestHeadersSpec);
        Mockito.when(webClient.delete()).thenReturn(requestHeadersUriSpec);
        Mockito.when(webClientBuilder.build()).thenReturn(webClient);
        Assertions.assertEquals("User id not found", appUserService.deleteUser("id"));
    }

    @Test
    void testDeleteUsersPlants() {
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        Mockito.when(responseSpec.bodyToMono(Boolean.class)).thenReturn(mono);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(requestHeadersUriSpec.uri("url")).thenReturn(requestHeadersSpec);
        Mockito.when(webClient.delete()).thenReturn(requestHeadersUriSpec);
        Mockito.when(webClientBuilder.build()).thenReturn(webClient);
        Assertions.assertInstanceOf(Mono.class, appUserService.deleteUsersPlants("url"));
    }

    @Test
    void testGetUserById() throws EntityNotFoundException {
        AppUser appUser = Mockito.mock(AppUser.class);
        Mockito.when(appUserRepository.findById("id")).thenReturn(Optional.of(appUser));
        Assertions.assertNull(appUserService.getUserById("id"));
    }

}
