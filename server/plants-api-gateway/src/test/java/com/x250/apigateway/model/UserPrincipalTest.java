package com.x250.apigateway.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class UserPrincipalTest {

    @Mock
    Collection<? extends GrantedAuthority> authorities;

    @Mock
    AppUser appUser;

    @Mock
    Role role;

    @Mock
    Map<String, Object> attributes;

    @Test
    void testConstructorAndGetters() {
        UserPrincipal userPrincipal = new UserPrincipal("id", "email", "password", authorities);
        Assertions.assertEquals("email", userPrincipal.getUsername());
        Assertions.assertEquals("password", userPrincipal.getPassword());
        Assertions.assertTrue(userPrincipal.isAccountNonExpired());
        Assertions.assertTrue(userPrincipal.isAccountNonLocked());
        Assertions.assertTrue(userPrincipal.isCredentialsNonExpired());
        Assertions.assertTrue(userPrincipal.isEnabled());
    }

    @Test
    void testCreate() {
        Mockito.when(appUser.getRole()).thenReturn(role);
        UserPrincipal userPrincipal = UserPrincipal.create(appUser);
        Assertions.assertInstanceOf(UserPrincipal.class, userPrincipal);
    }

    @Test
    void testCreateWithAttributes() {
        Mockito.when(appUser.getRole()).thenReturn(role);
        UserPrincipal userPrincipal = UserPrincipal.create(appUser, attributes);
        Assertions.assertFalse(userPrincipal.getAttributes().isEmpty());
    }

}
