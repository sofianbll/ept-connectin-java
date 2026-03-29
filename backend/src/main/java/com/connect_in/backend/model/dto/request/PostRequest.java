package com.connect_in.backend.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {

    @NotBlank(message = "Captions are required")
    private String captions;

    // image can be null, it's optional
    private String image_path;

}