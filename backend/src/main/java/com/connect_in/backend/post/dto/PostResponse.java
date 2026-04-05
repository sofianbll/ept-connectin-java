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
    // Use "user" instead of "author" (frontend contract).
    private UserDTO user;
    private int likesCount;
    // @JsonProperty forces "is_liked" in JSON (SNAKE_CASE would yield "liked").
    @JsonProperty("is_liked")
    private boolean liked;
    private int commentsCount;
    private List<CommentResponse> comments;
    private LocalDateTime createdAt;
}
