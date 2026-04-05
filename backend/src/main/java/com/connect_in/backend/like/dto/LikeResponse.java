package com.connect_in.backend.like.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {
    // Contrat frontend : { "is_liked": bool, "likes_count": int }
    @JsonProperty("is_liked")
    private boolean liked;
    private int likesCount;
}
