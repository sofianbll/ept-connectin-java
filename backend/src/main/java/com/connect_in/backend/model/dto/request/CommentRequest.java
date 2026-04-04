package com.connect_in.backend.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class CommentRequest {

    @NotBlank(message = "Comments are required")
    @Size(max = 250, message = "Cannot be more than 250 characters")
    private String text;
}