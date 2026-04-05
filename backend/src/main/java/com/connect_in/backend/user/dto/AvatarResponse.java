package com.connect_in.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvatarResponse {
    // Contrat frontend : { "avatar": "/storage/avatars/filename.jpg" }
    private String avatar;
}
