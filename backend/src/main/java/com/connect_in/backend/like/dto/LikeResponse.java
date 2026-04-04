package com.connect_in.backend.like.dto;

import lombok.Data;

@Data
public class LikeResponse {
    private Long id;
    private Long postId;
    private Long userId;
}