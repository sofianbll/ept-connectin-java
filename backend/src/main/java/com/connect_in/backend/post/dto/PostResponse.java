package com.connect_in.backend.post.dto;

import com.connect_in.backend.user.dto.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long id;
    private String captions;
    private String imagePath;
    private UserDTO author;
    private int likesCount;
    private boolean isLiked;
    private int commentsCount;
    private LocalDateTime createdAt;
}
