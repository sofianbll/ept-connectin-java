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

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 255, message = "Name must contain between 2 < AND > 255 characters")
    private String name;
}