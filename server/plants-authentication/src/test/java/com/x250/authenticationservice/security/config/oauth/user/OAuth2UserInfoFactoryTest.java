package com.x250.authenticationservice.security.config.oauth.user;

import com.x250.authenticationservice.exception.OAuth2AuthenticationProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
class OAuth2UserInfoFactoryTest {

    @Mock
    Map<String, Object> attributes;

    @Test
    void testGet() {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.get("google", attributes);
        Assertions.assertInstanceOf(OAuth2UserInfo.class, oAuth2UserInfo);
        Assertions.assertFalse(oAuth2UserInfo.attributes.isEmpty());
    }

    @Test
    void testGetException() {

        OAuth2AuthenticationProcessingException oAuth2AuthenticationProcessingException = Assertions.assertThrows(OAuth2AuthenticationProcessingException.class, () -> OAuth2UserInfoFactory.get("registration id", attributes));
        Assertions.assertEquals("registration id is not supported", oAuth2AuthenticationProcessingException.getMessage());
    }

}
