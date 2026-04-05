package com.connect_in.backend.auth;

import com.connect_in.backend.auth.dto.AuthResponse;
import com.connect_in.backend.auth.dto.LoginRequest;
import com.connect_in.backend.auth.dto.RegisterRequest;
import com.connect_in.backend.shared.exception.ValidationException;
import com.connect_in.backend.shared.security.JwtService;
import com.connect_in.backend.user.User;
import com.connect_in.backend.user.UserRepository;
import com.connect_in.backend.user.Role;
import com.connect_in.backend.user.dto.UserDTO;
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
            throw new ValidationException("Passwords do not match.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already in use: " + request.getEmail());
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
