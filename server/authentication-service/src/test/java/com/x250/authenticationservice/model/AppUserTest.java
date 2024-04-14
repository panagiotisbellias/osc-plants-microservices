package com.x250.authenticationservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppUserTest {

    @Test
    void testAllArgsConstructor() {
        Role role = Mockito.mock(Role.class);
        AuthProvider provider = Mockito.mock(AuthProvider.class);
        AppUser appUser = new AppUser("id", "username", "email", "image url", true, "password", role, provider, "provider id");

        Assertions.assertInstanceOf(AppUser.class, appUser);
        Assertions.assertEquals("id", appUser.getId());
        Assertions.assertEquals("username", appUser.getUsername());
        Assertions.assertEquals("email", appUser.getEmail());
        Assertions.assertEquals("image url", appUser.getImageUrl());
        Assertions.assertTrue(appUser.getEmailVerified());
        Assertions.assertEquals("password", appUser.getPassword());
        Assertions.assertEquals(role, appUser.getRole());
        Assertions.assertEquals(provider, appUser.getProvider());
        Assertions.assertEquals("provider id", appUser.getProviderId());
    }

    @Test
    void testNoArgsConstructor() {
        AppUser appUser = new AppUser();
        Assertions.assertInstanceOf(AppUser.class, appUser);
        Assertions.assertFalse(appUser.getEmailVerified());
    }

}
