package com.connect_in.backend.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @Email(message = "Invalid email address.")
    private String email;

    @Size(min = 2, max = 255)
    @JsonProperty("firstName")
    private String firstName;

    @Size(min = 2, max = 255)
    @JsonProperty("lastName")
    private String lastName;

    // Password change fields (optional, validated together in service).
    @JsonProperty("current_password")
    private String currentPassword;

    @JsonProperty("new_password")
    private String newPassword;

    @JsonProperty("new_password_confirmation")
    private String newPasswordConfirmation;
}
