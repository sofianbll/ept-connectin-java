package com.connect_in.backend.model.dto.response;

import com.connect_in.backend.model.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String text;
    private LocalDateTime createdAt;
}