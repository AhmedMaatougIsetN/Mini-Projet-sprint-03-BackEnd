package tn.isetn.laptopsapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final String secret;
    private final long expirationMs;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, String secret, long expirationMs) {
        this.objectMapper = objectMapper; this.secret = secret; this.expirationMs = expirationMs;
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/login");
    }

    @Override public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Credentials credentials = objectMapper.readValue(request.getInputStream(), Credentials.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password()));
        } catch (IOException exception) {
            throw new org.springframework.security.authentication.BadCredentialsException("Identifiants invalides", exception);
        }
    }

    @Override protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        Date now = new Date();
        String token = JWT.create().withSubject(authentication.getName()).withArrayClaim("roles", roles.toArray(new String[0]))
            .withIssuedAt(now).withExpiresAt(new Date(now.getTime() + expirationMs)).sign(Algorithm.HMAC256(secret));
        response.setHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json"); response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getOutputStream(), Map.of("token", token, "username", authentication.getName(), "roles", roles));
    }

    @Override protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json"); response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getOutputStream(), Map.of("message", "Nom d'utilisateur ou mot de passe incorrect"));
    }

    private record Credentials(String username, String password) {}
}
