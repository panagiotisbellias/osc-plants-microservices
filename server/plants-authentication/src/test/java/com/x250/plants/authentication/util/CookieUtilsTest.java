package com.x250.plants.authentication.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CookieUtilsTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    Cookie cookie;

    @Test
    void testGetCookie() {
        Mockito.when(cookie.getName()).thenReturn("testCookie3");
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        Assertions.assertEquals(Optional.of(cookie), CookieUtils.getCookie(request, "testCookie3"));
    }

    @Test
    void testGetCookieEmpty() {
        Assertions.assertEquals(Optional.empty(), CookieUtils.getCookie(request, "name"));
    }

    @Test
    void testGetCookieNoMatch() {
        Mockito.when(cookie.getName()).thenReturn("name1");
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        Assertions.assertEquals(Optional.empty(), CookieUtils.getCookie(request, "testCookie4"));
    }

    @Test
    void testAddCookie() {
        CookieUtils.addCookie(response, "testCookie2", "value", 0);
        Mockito.verify(response).addCookie(ArgumentMatchers.any(Cookie.class));
    }

    @Test
    void testDeleteCookie() {
        Mockito.when(cookie.getName()).thenReturn("testCookie1");
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        CookieUtils.deleteCookie(request, response, "testCookie1");

        Mockito.verify(request).getCookies();
        Mockito.verify(cookie, Mockito.times(2)).getName();
        Mockito.verify(cookie).setValue("");
        Mockito.verify(cookie).setPath("/");
        Mockito.verify(cookie).setMaxAge(0);
        Mockito.verify(response).addCookie(cookie);
    }

    @Test
    void testDeleteCookieNull() {
        CookieUtils.deleteCookie(request, response, "name");
        verifyCommonInteractions();
    }

    @Test
    void testDeleteCookieNoMatch() {
        Mockito.when(cookie.getName()).thenReturn("name1");
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        CookieUtils.deleteCookie(request, response, "testCookie5");

        Mockito.verify(cookie).getName();
        verifyCommonInteractions();
    }

    void verifyCommonInteractions() {
        Mockito.verify(request).getCookies();
        Mockito.verifyNoInteractions(response);
        Mockito.verifyNoMoreInteractions(cookie);
    }

    @Test
    void testSerialize() {
        OAuth2AuthorizationRequest authorizationRequest = Mockito.mock(OAuth2AuthorizationRequest.class);
        Assertions.assertEquals("rO0ABXNyAExvcmcuc3ByaW5nZnJhbWV3b3JrLnNlY3VyaXR5Lm9hdXRoMi5jb3JlLmVuZHBvaW50Lk9BdXRoMkF1dGhvcml6YXRpb25SZXF1ZXN0AAAAAAAAAmwCAApMABRhZGRpdGlvbmFsUGFyYW1ldGVyc3QAD0xqYXZhL3V0aWwvTWFwO0wACmF0dHJpYnV0ZXNxAH4AAUwAFmF1dGhvcml6YXRpb25HcmFudFR5cGV0AEFMb3JnL3NwcmluZ2ZyYW1ld29yay9zZWN1cml0eS9vYXV0aDIvY29yZS9BdXRob3JpemF0aW9uR3JhbnRUeXBlO0wAF2F1dGhvcml6YXRpb25SZXF1ZXN0VXJpdAASTGphdmEvbGFuZy9TdHJpbmc7TAAQYXV0aG9yaXphdGlvblVyaXEAfgADTAAIY2xpZW50SWRxAH4AA0wAC3JlZGlyZWN0VXJpcQB-AANMAAxyZXNwb25zZVR5cGV0AFNMb3JnL3NwcmluZ2ZyYW1ld29yay9zZWN1cml0eS9vYXV0aDIvY29yZS9lbmRwb2ludC9PQXV0aDJBdXRob3JpemF0aW9uUmVzcG9uc2VUeXBlO0wABnNjb3Blc3QAD0xqYXZhL3V0aWwvU2V0O0wABXN0YXRlcQB-AAN4cHBwcHBwcHBwcHA=",
                CookieUtils.serialize(authorizationRequest));
    }

    @Test
    void testDeserialize() {
        Mockito.when(cookie.getValue()).thenReturn("rO0ABXNyAExvcmcuc3ByaW5nZnJhbWV3b3JrLnNlY3VyaXR5Lm9hdXRoMi5jb3JlLmVuZHBvaW50Lk9BdXRoMkF1dGhvcml6YXRpb25SZXF1ZXN0AAAAAAAAAmwCAApMABRhZGRpdGlvbmFsUGFyYW1ldGVyc3QAD0xqYXZhL3V0aWwvTWFwO0wACmF0dHJpYnV0ZXNxAH4AAUwAFmF1dGhvcml6YXRpb25HcmFudFR5cGV0AEFMb3JnL3NwcmluZ2ZyYW1ld29yay9zZWN1cml0eS9vYXV0aDIvY29yZS9BdXRob3JpemF0aW9uR3JhbnRUeXBlO0wAF2F1dGhvcml6YXRpb25SZXF1ZXN0VXJpdAASTGphdmEvbGFuZy9TdHJpbmc7TAAQYXV0aG9yaXphdGlvblVyaXEAfgADTAAIY2xpZW50SWRxAH4AA0wAC3JlZGlyZWN0VXJpcQB-AANMAAxyZXNwb25zZVR5cGV0AFNMb3JnL3NwcmluZ2ZyYW1ld29yay9zZWN1cml0eS9vYXV0aDIvY29yZS9lbmRwb2ludC9PQXV0aDJBdXRob3JpemF0aW9uUmVzcG9uc2VUeXBlO0wABnNjb3Blc3QAD0xqYXZhL3V0aWwvU2V0O0wABXN0YXRlcQB-AAN4cHBwcHBwcHBwcHA=");
        Assertions.assertInstanceOf(OAuth2AuthorizationRequest.class, CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class));
    }

}
