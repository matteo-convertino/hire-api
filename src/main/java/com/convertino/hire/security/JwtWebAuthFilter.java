package com.convertino.hire.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static com.convertino.hire.utils.CookieUtils.ACCESS_TOKEN_COOKIE;

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
            Cookie[] cookies = request.getCookies();

            String token = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (ACCESS_TOKEN_COOKIE.equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            jwtFilterHelper.authenticateUser(request, token);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}