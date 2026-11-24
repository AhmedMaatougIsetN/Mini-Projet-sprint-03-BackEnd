package tn.isetn.laptopsapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception { return configuration.getAuthenticationManager(); }

    @Bean CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("http://localhost:4200"));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        cors.setExposedHeaders(List.of("Authorization", "Location"));
        cors.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**", cors); return source;
    }

    @Bean SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager manager, ObjectMapper mapper,
            @Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-ms}") long expirationMs) throws Exception {
        JWTAuthenticationFilter loginFilter = new JWTAuthenticationFilter(manager, mapper, secret, expirationMs);
        JWTAuthorizationFilter authorizationFilter = new JWTAuthorizationFilter(secret);
        http.cors(cors -> {}).csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api", "/api/**", "/rest", "/rest/**", "/cat", "/cat/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/api/**", "/rest/**", "/cat/**").hasAuthority("ADMIN")
                .anyRequest().authenticated())
            .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
