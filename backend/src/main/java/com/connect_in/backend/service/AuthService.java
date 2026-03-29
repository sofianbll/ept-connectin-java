package com.connect_in.backend.service;

import com.connect_in.backend.exception.ValidationException;
import com.connect_in.backend.model.dto.UserDTO;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            throw new ValidationException("Les mots de passe ne correspondent pas");
        }

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
        return buildAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        return buildAuthResponse(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                null,
                user.getRole().name()
        );
        return new AuthResponse(jwtService.generateToken(user), userDTO);
    }
}
