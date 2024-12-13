package com.sergosoft.productservice.config.noauth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Slf4j
@Profile("no-auth")
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(NoAuthProperties.class)
public class NoAuthSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.warn("All requests are permitted in no-auth profile");
        http
                .cors(CorsConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public OncePerRequestFilter authenticationFilter(NoAuthProperties noAuthProperties) {
        // Create a custom filter to simulate a JWT token for all requests
        return new AuthenticationFilter(noAuthProperties.getClaims());
    }

    private class AuthenticationFilter extends OncePerRequestFilter {

        private final Map<String, Object> claims;

        public AuthenticationFilter(Map<String, Object> claims) {
            this.claims = claims;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            // Create a fake JWT token
            Jwt jwt = Jwt.withTokenValue("dummy-token")
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plus(Duration.ofMinutes(30)))
                    .header("alg", "RS256")
                    .claims(headers -> headers.putAll(claims))
                    .build();

            // Wrap the JWT in a JwtAuthenticationToken
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt);

            // Set the authentication context with the simulated JWT token
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(jwtAuthenticationToken);

            // Continue the filter chain
            filterChain.doFilter(request, response);
        }
    }
}
