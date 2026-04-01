package com.connect_in.backend.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class CommentRequest {

    @NotBlank(message = "Comments are required")
    @Size(min = 10, message = "Text must be at least 10 characters")
    private String text;
}