package com.convertino.hire.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeUnit;

public class CookieUtils {
    public static String ACCESS_TOKEN_COOKIE = "access-token";

    public static void setJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(365));
        // cookie.setDomain("yourdomain.com");

        response.addCookie(cookie);
    }

    public static void clearJwtCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
