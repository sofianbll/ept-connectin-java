package com.connect_in.backend.model.dto.response;

import com.connect_in.backend.model.dto.UserDTO;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String text;
    private UserDTO author;
    private LocalDateTime createdAt;
}