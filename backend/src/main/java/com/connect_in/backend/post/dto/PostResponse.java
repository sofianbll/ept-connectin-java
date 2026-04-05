package com.connect_in.backend.post.dto;

import com.connect_in.backend.comment.dto.CommentResponse;
import com.connect_in.backend.user.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private String captions;
    private String imagePath;
    // "user" et non "author" — contrat frontend
    private UserDTO user;
    private int likesCount;
    // @JsonProperty force "is_liked" en JSON (SNAKE_CASE global donnerait "liked" pour un boolean)
    @JsonProperty("is_liked")
    private boolean liked;
    private int commentsCount;
    private List<CommentResponse> comments;
    private LocalDateTime createdAt;
}
