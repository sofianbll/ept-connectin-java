package com.connect_in.backend.like;

import com.connect_in.backend.like.dto.LikeRequest;
import com.connect_in.backend.like.dto.LikeResponse;
import com.connect_in.backend.user.User;
import com.connect_in.backend.like.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<LikeResponse> likePost(@Valid @RequestBody LikeRequest request, @AuthenticationPrincipal User currentUser){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(likeService.likePost(request, currentUser));
    }

    @DeleteMapping("/posts/{postId}/likes")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId, @AuthenticationPrincipal User currentUser){
        likeService.unlikePost(postId, currentUser);
        return ResponseEntity.noContent().build();
    }

}
