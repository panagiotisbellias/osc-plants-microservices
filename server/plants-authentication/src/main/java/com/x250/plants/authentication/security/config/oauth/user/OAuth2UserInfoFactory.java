package com.x250.plants.authentication.security.config.oauth.user;

import com.x250.plants.authentication.exception.OAuth2AuthenticationProcessingException;
import com.x250.plants.authentication.model.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    private OAuth2UserInfoFactory() {
        // empty constructor
    }

    public static OAuth2UserInfo get(String registrationId, Map<String, Object> attributes){
        if (AuthProvider.GOOGLE.name().equalsIgnoreCase(registrationId)){
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(registrationId + " is not supported");
        }
    }
}