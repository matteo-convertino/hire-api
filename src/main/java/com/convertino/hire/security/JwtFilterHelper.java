package com.convertino.hire.security;

import com.convertino.hire.model.User;
import com.convertino.hire.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtFilterHelper {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public void authenticateUser(HttpServletRequest request, HttpServletResponse response, String token, boolean isWeb) {
        String username = jwtService.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = (User) userDetailsService.loadUserByUsername(username);

            if (!jwtService.isTokenExpired(token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else if (isWeb) CookieUtils.clearJwtCookie(response);
        }
    }
}
