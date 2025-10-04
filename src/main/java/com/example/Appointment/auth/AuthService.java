package com.example.Appointment.auth;

import com.example.Appointment.auth.dto.*;
import com.example.Appointment.security.JwtService;
import com.example.Appointment.user.Role;
import com.example.Appointment.user.User;
import com.example.Appointment.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public TokenResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already used");
        }

        Role role = (req.getRole() == null) ? Role.USER : req.getRole();

        // Without Lombok builder:
        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(role);
        userRepository.save(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("name", user.getFullName());

        String access = jwtService.generateAccessToken(user.getEmail(), claims);
        String refresh = jwtService.generateRefreshToken(user.getEmail());

        user.setRefreshToken(refresh);
        user.setRefreshTokenExpiry(jwtService.getExpiry(refresh));
        userRepository.save(user);

        return new TokenResponse("Register successfully", access, refresh);
    }

    public TokenResponse login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        User user = userRepository.findByEmail(req.getEmail()).orElseThrow();

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("name", user.getFullName());

        String access = jwtService.generateAccessToken(user.getEmail(), claims);
        String refresh = jwtService.generateRefreshToken(user.getEmail());

        user.setRefreshToken(refresh);
        user.setRefreshTokenExpiry(jwtService.getExpiry(refresh));
        userRepository.save(user);

        return new TokenResponse("Login successfully", access, refresh);
    }

    public TokenResponse refresh(RefreshRequest req) {
        if (!jwtService.isValid(req.getRefreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String email = jwtService.extractSubject(req.getRefreshToken());
        User user = userRepository.findByEmail(email).orElseThrow();

        if (user.getRefreshToken() == null || !user.getRefreshToken().equals(req.getRefreshToken())) {
            throw new IllegalArgumentException("Refresh token not recognized");
        }
        if (user.getRefreshTokenExpiry() == null || user.getRefreshTokenExpiry().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("name", user.getFullName());

        String access = jwtService.generateAccessToken(user.getEmail(), claims);
        String newRefresh = jwtService.generateRefreshToken(user.getEmail());

        user.setRefreshToken(newRefresh);
        user.setRefreshTokenExpiry(jwtService.getExpiry(newRefresh));
        userRepository.save(user);

        return new TokenResponse("Token refreshed", access, newRefresh);
    }

    public void logout(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setRefreshToken(null);
        user.setRefreshTokenExpiry(null);
        userRepository.save(user);
    }
}
