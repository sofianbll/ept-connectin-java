package com.connect_in.backend.service;

import com.connect_in.backend.exception.ValidationException;
import com.connect_in.backend.model.dto.request.LoginRequest;
import com.connect_in.backend.model.dto.request.RegisterRequest;
import com.connect_in.backend.model.dto.response.AuthResponse;
import com.connect_in.backend.model.entity.Role;
import com.connect_in.backend.model.entity.User;
import com.connect_in.backend.repository.UserRepository;
import com.connect_in.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email déjà utilisé : " + request.getEmail());
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(user));
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow();

        return new AuthResponse(jwtService.generateToken(user));
    }
}