package com.connect_in.backend.model.dto.response;

import com.connect_in.backend.model.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private String captions;
    private String image_path;
    private UserDTO author;
    private int likesCount;
    private boolean isLiked;
    private int commentsCount;
    private LocalDateTime created_at;

}