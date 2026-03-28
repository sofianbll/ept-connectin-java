package com.connect_in.backend.model.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
