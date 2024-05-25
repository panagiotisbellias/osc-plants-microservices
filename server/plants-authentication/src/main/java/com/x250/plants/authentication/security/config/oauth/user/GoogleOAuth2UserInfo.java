package com.x250.plants.authentication.security.config.oauth.user;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GoogleOAuth2UserInfo extends OAuth2UserInfo{

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        log.debug("getId()");
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        log.debug("getName()");
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        log.debug("getEmail()");
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        log.debug("getImageUrl()");
        return (String) attributes.get("picture");
    }
}