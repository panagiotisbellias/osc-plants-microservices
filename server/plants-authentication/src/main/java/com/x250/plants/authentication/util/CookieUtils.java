package com.x250.plants.authentication.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.SerializationUtils;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

//TODO Extract common code before logging issue is closed
@Slf4j
public class CookieUtils {

    private CookieUtils() {
        // empty constructor
    }

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        log.debug("getCookie({}, {})", request.getClass(), name);
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            log.info("Cookies: {}", Arrays.toString(cookies));
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    log.info("Cookie {} is found", name);
                    return Optional.of(cookie);
                }
            }
            log.warn("Cookie {} is not found", name);
        }

        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        log.debug("addCookie({}, {}, {}, {})", response.getClass(), name, value, maxAge);
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        log.info("Cookie is instantiated");
        response.addCookie(cookie);
        log.info("Cookie {} is added to the response", cookie.getName());
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        log.debug("deleteCookie({}, {}, {})", request.getClass(), response.getClass(), name);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            log.info("Cookies: {}", Arrays.toString(cookies));
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(name)) {
                    log.info("Cookie {} is found", name);
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    log.info("Cookie is updated");
                    response.addCookie(cookie);
                    log.info("Cookie {} is added to the response", cookie.getName());
                    break;
                }
            }
            log.info("Cookie {} is not found", name);
            return;
        }
        log.warn("No cookies in request");
    }

    public static String serialize(Object object) {
        log.debug("serialize({})", object.getClass());
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        log.debug("deserialize({}, {})", cookie.getClass(), cls.getClasses());
        return cls.cast(SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())));
    }

}