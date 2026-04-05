package com.connect_in.backend.comment.dto;

import com.connect_in.backend.user.dto.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private String text;
    // "user" et non "author" — contrat frontend
    private UserDTO user;
    private LocalDateTime createdAt;
}
