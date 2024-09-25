package com.pms.pms.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(management ->
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Use stateless session management
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/auth/signup", "/auth/login").permitAll()  // Allow public access to signup and login
                                .requestMatchers("/api/**").authenticated()  // Protect other API routes
                                .anyRequest().permitAll())  // Allow public access to any other routes
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)  // Add JWT token validator before BasicAuthenticationFilter
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for stateless APIs
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));  // Enable CORS
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Arrays.asList(
                    "http://localhost:5173",
                    "http://localhost:3000",
                    "http://localhost:4200"
            ));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.addAllowedHeader("*");
            config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
            config.setMaxAge(3600L);
            return config;
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
