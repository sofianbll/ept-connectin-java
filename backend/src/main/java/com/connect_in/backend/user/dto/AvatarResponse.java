package com.connect_in.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvatarResponse {
    // Frontend contract: { "avatar": "/storage/avatars/filename.jpg" }
    private String avatar;
}
