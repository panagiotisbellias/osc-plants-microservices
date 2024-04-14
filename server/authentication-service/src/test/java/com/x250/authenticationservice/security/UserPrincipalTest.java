package com.x250.authenticationservice.security;

import com.x250.authenticationservice.model.AppUser;
import com.x250.authenticationservice.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class UserPrincipalTest {

    @InjectMocks
    UserPrincipal userPrincipal;

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
        UserPrincipal userPrincipal = new UserPrincipal("id", "name", "email", "password", authorities);
        Assertions.assertEquals("id", userPrincipal.getId());
        Assertions.assertEquals("name", userPrincipal.getName());
        Assertions.assertEquals("email", userPrincipal.getUsername());
        Assertions.assertEquals("password", userPrincipal.getPassword());
        Assertions.assertNull(userPrincipal.getAttributes());
    }

    @Test
    void testCreate() {
        Mockito.when(appUser.getRole()).thenReturn(role);
        Assertions.assertInstanceOf(UserPrincipal.class, UserPrincipal.create(appUser));
    }

    @Test
    void testCreateWithAttributes() {
        Mockito.when(appUser.getRole()).thenReturn(role);
        Assertions.assertInstanceOf(UserPrincipal.class, UserPrincipal.create(appUser, attributes));
    }

    @Test
    void testIsAccountNonExpired() {
        Assertions.assertTrue(userPrincipal.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        Assertions.assertTrue(userPrincipal.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        Assertions.assertTrue(userPrincipal.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        Assertions.assertTrue(userPrincipal.isEnabled());
    }

}
