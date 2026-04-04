package com.connect_in.backend.auth.dto;

import com.connect_in.backend.user.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;
    private UserDTO user;
}
