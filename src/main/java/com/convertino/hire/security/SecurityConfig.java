package com.convertino.hire.security;

import com.convertino.hire.utils.Role;
import com.convertino.hire.utils.routes.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Security configuration class for the application.
 * <p>
 * This class configures the security settings, including role hierarchy, method security, and HTTP security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtWebAuthFilter jwtWebAuthFilter;
    private final HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Bean definition for role hierarchy.
     *
     * @return the role hierarchy
     */
    @Bean
    static RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy(
                """
                            ROLE_ADMIN > ROLE_MODERATOR
                            ROLE_MODERATOR > ROLE_GUEST
                        """
        );
    }

    /**
     * Bean definition for method security expression handler.
     *
     * @param roleHierarchy the role hierarchy
     * @return the method security expression handler
     */
    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Bean definition for security filter chain.
     *
     * @param http the HTTP security
     * @return the security filter chain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        // Authentication endpoints
                        .requestMatchers(GET, AuthRoutes.USER).authenticated()
                        .requestMatchers(GET, WebAuthRoutes.LOGOUT).authenticated()
                        .requestMatchers(AuthRoutes.ALL).permitAll()
                        .requestMatchers(WebAuthRoutes.ALL).permitAll()

                        // JobPosition endpoints
                        .requestMatchers(GET, JobPositionRoutes.FIND_ALL).permitAll()
                        .requestMatchers(GET, JobPositionRoutes.ALL).permitAll()
                        .requestMatchers(POST, JobPositionRoutes.SAVE).hasRole(Role.MODERATOR.getRole())
                        .requestMatchers(JobPositionRoutes.ALL).hasRole(Role.MODERATOR.getRole())

                        // Skill endpoints
                        .requestMatchers(POST, SkillRoutes.SAVE).hasRole(Role.MODERATOR.getRole())
                        .requestMatchers(SkillRoutes.ALL).hasRole(Role.MODERATOR.getRole())

                        // Interview endpoints
                        .requestMatchers(POST, InterviewRoutes.SAVE).hasRole(Role.GUEST.getRole())
                        .requestMatchers(InterviewRoutes.FIND_BY_ID).hasRole(Role.GUEST.getRole())
                        .requestMatchers(InterviewRoutes.ALL).hasRole(Role.MODERATOR.getRole())

                        // Message endpoints
                        .requestMatchers(MessageRoutes.ALL).authenticated()

                        // Report endpoints
                        .requestMatchers(ReportRoutes.ALL).hasRole(Role.MODERATOR.getRole())

                        // WebSocket endpoints
                        .requestMatchers("/ws").hasRole(Role.GUEST.getRole())

                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationManager(authenticationManager)
                // Exception handling: AuthenticationException, AccessDeniedException
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> handlerExceptionResolver.resolveException(request, response, null, authException))
                        .accessDeniedHandler((request, response, accessDeniedException) -> handlerExceptionResolver.resolveException(request, response, null, accessDeniedException))
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtWebAuthFilter, JwtAuthFilter.class);

        return http.build();
    }
}