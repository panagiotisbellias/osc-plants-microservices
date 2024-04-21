package com.x250.authenticationservice.security.config.oauth;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

@ExtendWith(MockitoExtension.class)
class CustomOauth2UserServiceTest {

    @InjectMocks
    CustomOauth2UserService customOauth2UserService;

    @Disabled("The GET request should be mocked")
    @Test
    void testLoadUser() {
        OAuth2UserRequest userRequest = Mockito.mock(OAuth2UserRequest.class);
        ClientRegistration clientRegistration = Mockito.mock(ClientRegistration.class);
        ClientRegistration.ProviderDetails providerDetails = Mockito.mock(ClientRegistration.ProviderDetails.class);
        ClientRegistration.ProviderDetails.UserInfoEndpoint userInfoEndpoint = Mockito.mock(ClientRegistration.ProviderDetails.UserInfoEndpoint.class);
        OAuth2AccessToken oAuth2AccessToken = Mockito.mock(OAuth2AccessToken.class);

        Mockito.when(userInfoEndpoint.getUri()).thenReturn("http://www.test.com");
        Mockito.when(userInfoEndpoint.getUserNameAttributeName()).thenReturn("user name");
        Mockito.when(providerDetails.getUserInfoEndpoint()).thenReturn(userInfoEndpoint);
        Mockito.when(clientRegistration.getProviderDetails()).thenReturn(providerDetails);
        Mockito.when(userRequest.getAccessToken()).thenReturn(oAuth2AccessToken);
        Mockito.when(userRequest.getClientRegistration()).thenReturn(clientRegistration);
        customOauth2UserService.loadUser(userRequest);
    }

}
