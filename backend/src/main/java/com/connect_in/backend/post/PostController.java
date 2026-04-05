package com.connect_in.backend.post;

import com.connect_in.backend.post.dto.PostResponse;
import com.connect_in.backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // GET /api/posts?teamId=  → retourne List<> (pas Page<>) — contrat frontend
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestParam(required = false) Long teamId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.getAllPosts(teamId, currentUser));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> getPostById(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.getPostById(id, currentUser));
    }

    // POST /api/posts — multipart/form-data (captions + image optionnelle + teamId optionnel)
    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> createPost(
            @RequestParam String captions,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) Long teamId,
            @AuthenticationPrincipal User currentUser) {
        PostResponse created = postService.createPost(captions, image, teamId, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/posts/{id} — multipart/form-data
    @PutMapping(value = "/posts/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @RequestParam(required = false) String captions,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) Long teamId,
            @AuthenticationPrincipal User currentUser) {
        PostResponse updated = postService.updatePost(id, captions, image, teamId, currentUser);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        postService.deletePost(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
