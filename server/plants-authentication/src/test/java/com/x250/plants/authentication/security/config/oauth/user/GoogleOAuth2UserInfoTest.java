package com.x250.plants.authentication.security.config.oauth.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
class GoogleOAuth2UserInfoTest {

    @Mock
    Map<String, Object> attributes;

    @Test
    void testConstructorAndGetters() {

        Mockito.when(attributes.get("sub")).thenReturn("id");
        Mockito.when(attributes.get("name")).thenReturn("test name");
        Mockito.when(attributes.get("email")).thenReturn("test email");
        Mockito.when(attributes.get("picture")).thenReturn("test image url");

        GoogleOAuth2UserInfo googleOAuth2UserInfo = new GoogleOAuth2UserInfo(attributes);
        Assertions.assertEquals("id", googleOAuth2UserInfo.getId());
        Assertions.assertEquals("test name", googleOAuth2UserInfo.getName());
        Assertions.assertEquals("test email", googleOAuth2UserInfo.getEmail());
        Assertions.assertEquals("test image url", googleOAuth2UserInfo.getImageUrl());

    }

}
