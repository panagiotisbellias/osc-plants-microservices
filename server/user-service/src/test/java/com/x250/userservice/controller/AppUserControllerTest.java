package com.x250.userservice.controller;

import com.x250.userservice.dto.AppUserCreateDTO;
import com.x250.userservice.exception.EntityNotFoundException;
import com.x250.userservice.service.AppUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AppUserControllerTest {

    @InjectMocks
    AppUserController appUserController;

    @Mock
    AppUserService appUserService;

    @Mock
    RuntimeException ex;

    @Test
    void testConstructor() {
        AppUserController appUserController = new AppUserController(appUserService);
        Assertions.assertInstanceOf(AppUserController.class, appUserController);
    }

    @Test
    void testGetAllUsers() {
        Assertions.assertTrue(appUserController.getAllUsers().isEmpty());
    }

    @Test
    void testGetUserById() throws EntityNotFoundException {
        Assertions.assertNull(appUserController.getUserById("id"));
    }

    @Test
    void testAddUser() {
        AppUserCreateDTO appUserCreateDTO = Mockito.mock(AppUserCreateDTO.class);
        Assertions.assertNull(appUserController.addUser(appUserCreateDTO));
    }

    @Test
    void testDeleteUser() {
        Assertions.assertInstanceOf(CompletableFuture.class, appUserController.deleteUser("id"));
    }

    @Test
    void testDeleteUserRuntimeException() {
        Mockito.when(ex.getMessage()).thenReturn("runtime exception");
        Mockito.when(appUserService.deleteUser("id")).thenThrow(ex);
        Assertions.assertInstanceOf(CompletableFuture.class, appUserController.deleteUser("id"));
    }

    @Test
    void testFallbackMethod() {
        Mockito.when(ex.getMessage()).thenReturn("message");
        Assertions.assertInstanceOf(CompletableFuture.class, appUserController.fallbackMethod("id", ex));
    }

    @Test
    void testFallbackMethodMessageUserNotFound() {
        Mockito.when(ex.getMessage()).thenReturn("User id not found in database");
        Assertions.assertInstanceOf(CompletableFuture.class, appUserController.fallbackMethod("id", ex));
    }

}
