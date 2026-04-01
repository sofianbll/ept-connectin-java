package com.connect_in.backend.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LikeRequest {

    @NotBlank(message = "Captions are required")
    private int user_id;
    private int post_id;
}