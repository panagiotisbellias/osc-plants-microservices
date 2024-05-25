package com.x250.plants.authentication.security;

import com.x250.plants.authentication.model.AppUser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class UserPrincipal implements OAuth2User, UserDetails{

    private final String id;
    private final String name;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(String id, String name, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(AppUser appUser) {
        log.debug("create({})", appUser.toString());
        List<GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + appUser.getRole().toString()));
        log.info("Role granted for {}: {}", appUser.getUsername(), authorities.get(0));
        return new UserPrincipal(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getEmail(),
                appUser.getPassword(),
                authorities
        );
    }

    public static UserPrincipal create(AppUser appUser, Map<String, Object> attributes) {
        log.debug("create({}, {})", appUser.toString(), attributes.entrySet().toArray());
        UserPrincipal principal = create(appUser);
        principal.attributes = attributes;
        log.info("Principal created");
        return principal;
    }


    @Override
    public String getUsername() {
        log.debug("getUsername()");
        return email;
    }

    @Override
    public String getPassword() {
        log.debug("getPassword()");
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        log.debug("isAccountNonExpired()");
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        log.debug("isAccountNonLocked()");
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        log.debug("isCredentialsNonExpired()");
        return true;
    }

    @Override
    public boolean isEnabled() {
        log.debug("isEnabled()");
        return true;
    }

}