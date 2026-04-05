package com.connect_in.backend.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank(message = "Comment text is required.")
    @Size(max = 250, message = "Comment text cannot exceed 250 characters.")
    private String text;
}
