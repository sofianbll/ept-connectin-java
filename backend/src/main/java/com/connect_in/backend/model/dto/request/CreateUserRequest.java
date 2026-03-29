package com.connect_in.backend.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Mail is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 255)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 255)
    private String lastName;
}
