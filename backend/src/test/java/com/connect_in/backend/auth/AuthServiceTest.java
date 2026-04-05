package com.connect_in.backend.auth;

import com.connect_in.backend.auth.dto.RegisterRequest;
import com.connect_in.backend.shared.exception.ValidationException;
import com.connect_in.backend.shared.security.JwtService;
import com.connect_in.backend.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerThrowsWhenPasswordsMismatch() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("a@test.com");
        request.setPassword("p1");
        request.setPasswordConfirmation("p2");

        assertThrows(ValidationException.class, () -> authService.register(request));
    }
}
