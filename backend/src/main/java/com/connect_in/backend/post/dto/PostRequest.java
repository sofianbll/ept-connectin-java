package com.connect_in.backend.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {

    @NotBlank(message = "Captions are required.")
    private String captions;

    private String imagePath;

    private Long teamId;
}
