package com.connect_in.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 2, max = 255)
    private String firstName;

    @Size(min = 2, max = 255)
    private String lastName;

    // Changement de mot de passe (tous optionnels, mais validés ensemble en service)
    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirmation;
}
