package com.connect_in.backend.model.dto.response;

import com.connect_in.backend.model.dto.UserDTO;
import java.time.LocalDateTime;
import lombok.Data;

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