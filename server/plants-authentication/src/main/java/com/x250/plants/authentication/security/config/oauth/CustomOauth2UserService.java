package com.x250.plants.authentication.security.config.oauth;

import com.x250.plants.authentication.exception.OAuth2AuthenticationProcessingException;
import com.x250.plants.authentication.model.AppUser;
import com.x250.plants.authentication.model.AuthProvider;
import com.x250.plants.authentication.model.Role;
import com.x250.plants.authentication.repository.AppUserRepository;
import com.x250.plants.authentication.security.UserPrincipal;
import com.x250.plants.authentication.security.config.oauth.user.OAuth2UserInfo;
import com.x250.plants.authentication.security.config.oauth.user.OAuth2UserInfoFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final AppUserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("loadUser({})", userRequest.getClass());
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            log.info("Existing user: {}", oAuth2User.getName());
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            log.error(Arrays.toString(ex.getStackTrace()));
            throw ex;
        } catch (Exception e){
            log.error(Arrays.toString(e.getStackTrace()));
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        log.debug("processOAuth2User({}, {})", userRequest.getClass(), oAuth2User.getClass());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.get(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if(oAuth2UserInfo.getEmail().isEmpty()){
            log.error("Email not found from provider");
            throw new OAuth2AuthenticationProcessingException("Email not found from provider");
        }

        Optional<AppUser> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        AppUser appUser;
        if(userOptional.isPresent()){
            appUser = userOptional.get();
            //TODO check provider if equals
            appUser = update(appUser, oAuth2UserInfo, userRequest);
            log.info("App user's {} info updated", appUser.getUsername());
        } else {
            log.info("App user not found. Registering...");
            appUser = register(userRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(appUser, oAuth2User.getAttributes());
    }

    private AppUser register(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) {
        log.debug("register({}, {})", userRequest.getClass(), oAuth2UserInfo.getClass());
        AppUser appUser = new AppUser();

        appUser.setProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()));
        appUser.setProviderId(oAuth2UserInfo.getId());
        appUser.setUsername(oAuth2UserInfo.getName());
        appUser.setEmail(oAuth2UserInfo.getEmail());
        appUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        appUser.setEmailVerified(true);
        appUser.setRole(Role.USER);
        log.info("App user {} is created", appUser.getUsername());

        return userRepository.save(appUser);
    }

    private AppUser update(AppUser appUser, OAuth2UserInfo oAuth2UserInfo, OAuth2UserRequest userRequest) {
        log.debug("update({}, {}, {})", appUser.getClass(), oAuth2UserInfo.getClass(), userRequest.getClass());
        if (oAuth2UserInfo.getName() != null && oAuth2UserInfo.getImageUrl() != null) {
            log.info("Name and image URL are present for current user {}", oAuth2UserInfo.getName());
            appUser.setUsername(oAuth2UserInfo.getName());
            appUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        }
        // needed when user logs in first by username and password without OAuth2
        appUser.setProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()));
        appUser.setProviderId(oAuth2UserInfo.getId());
        appUser.setEmailVerified(true);
        log.info("App user {} is updated successfully", appUser.getUsername());

        return userRepository.save(appUser);
    }
}