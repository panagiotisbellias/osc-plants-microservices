package com.x250.plants.api.gateway.model;

import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
public class UserPrincipal implements UserDetails{

    private static final Log logger = LogFactory.getLog(UserPrincipal.class);

    private final String id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(String id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(AppUser appUser) {
        logger.debug(String.format("create(%s)", appUser));
        List<GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + appUser.getRole().toString()));
        logger.info(String.format("Authorities for app user %s are: %s", appUser, authorities.get(0).getAuthority()));
        return new UserPrincipal(
                appUser.getId(),
                appUser.getEmail(),
                appUser.getPassword(),
                authorities
        );
    }

    public static UserPrincipal create(AppUser appUser, Map<String, Object> attributes) {
        logger.debug(String.format("create(%s, %s)", appUser, Arrays.toString(attributes.entrySet().toArray())));
        UserPrincipal principal = create(appUser);
        principal.attributes = attributes;
        logger.info("User principal object successfully instantiated");
        return principal;
    }


    @Override
    public String getUsername() {
        logger.debug("getUsername()");
        return email;
    }

    @Override
    public String getPassword() {
        logger.debug("getPassword()");
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        logger.debug("isAccountNonExpired()");
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        logger.debug("isAccountNonLocked()");
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        logger.debug("isCredentialsNonExpired()");
        return true;
    }

    @Override
    public boolean isEnabled() {
        logger.debug("isEnabled()");
        return true;
    }

}