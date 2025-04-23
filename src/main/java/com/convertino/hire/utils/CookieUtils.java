package com.convertino.hire.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeUnit;

public class CookieUtils {
    public static String ACCESS_TOKEN_COOKIE = "access-token";
    public static String ACCESS_TOKEN_GUEST_COOKIE = "access-token-guest";

    public static void setJwtCookie(HttpServletResponse response, String token) {
        setJwtCookie(response, token, false);
    }

    public static void setJwtCookie(HttpServletResponse response, String token, boolean isGuest) {
        Cookie cookie = new Cookie(isGuest ? ACCESS_TOKEN_GUEST_COOKIE : ACCESS_TOKEN_COOKIE, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(365));
        // cookie.setDomain("yourdomain.com");

        response.addCookie(cookie);
    }

    public static void clearJwtCookie(HttpServletResponse response) {
        clearJwtCookie(response, false);
        clearJwtCookie(response, true);
    }

    public static void clearJwtCookie(HttpServletResponse response, boolean isGuest) {
        Cookie cookie = new Cookie(isGuest ? ACCESS_TOKEN_GUEST_COOKIE : ACCESS_TOKEN_COOKIE, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
