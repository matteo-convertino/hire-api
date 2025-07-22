package com.convertino.hire.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

import static com.convertino.hire.utils.CookieUtils.ACCESS_TOKEN_COOKIE;
import static com.convertino.hire.utils.CookieUtils.ACCESS_TOKEN_GUEST_COOKIE;

/**
 * Filter that processes JWT web authentication for each request.
 * <p>
 * Extends {@link OncePerRequestFilter} to ensure a single execution per request.
 */
@Component
@AllArgsConstructor
public class JwtWebAuthFilter extends OncePerRequestFilter {

    private final JwtFilterHelper jwtFilterHelper;
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final List<String> accessTokenGuestRoutes = List.of(
            "/api/v1/interviews",
            "/api/v1/messages",
            "/ws"
    );

    /**
     * Filters incoming requests to process JWT web authentication.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) {
        try {
            String token;

            if (accessTokenGuestRoutes.stream().anyMatch(request.getRequestURI()::startsWith)) {
                token = extractJwtFromCookie(request.getCookies(), ACCESS_TOKEN_GUEST_COOKIE);
            } else {
                token = extractJwtFromCookie(request.getCookies(), ACCESS_TOKEN_COOKIE);
            }

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            jwtFilterHelper.authenticateUser(request, response, token, true);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    private String extractJwtFromCookie(Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}