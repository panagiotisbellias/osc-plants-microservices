package com.x250.plants.authentication.security.config.oauth.user;

import com.x250.plants.authentication.exception.OAuth2AuthenticationProcessingException;
import com.x250.plants.authentication.model.AuthProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class OAuth2UserInfoFactory {

    private OAuth2UserInfoFactory() {
        // empty constructor
    }

    public static OAuth2UserInfo get(String registrationId, Map<String, Object> attributes){
        log.debug("get({}, {})", registrationId, Arrays.toString(attributes.entrySet().toArray()));
        if (AuthProvider.GOOGLE.name().equalsIgnoreCase(registrationId)){
            log.info("Registration id matches");
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            log.error("Registration id doesn't match");
            throw new OAuth2AuthenticationProcessingException(registrationId + " is not supported");
        }
    }
}