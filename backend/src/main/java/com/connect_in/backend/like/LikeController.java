package com.connect_in.backend.like;

import com.connect_in.backend.like.dto.LikeResponse;
import com.connect_in.backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // POST /api/posts/{id}/like — toggle like/unlike, contrat frontend
    @PostMapping("/posts/{id}/like")
    public ResponseEntity<LikeResponse> toggleLike(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(likeService.toggleLike(id, currentUser));
    }
}
