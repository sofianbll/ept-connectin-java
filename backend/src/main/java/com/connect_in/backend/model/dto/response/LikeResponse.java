package com.connect_in.backend.model.dto.response;

import lombok.Data;

@Data
public class LikeResponse {
    private Long id;
    private Long postId;
    private Long userId;
}