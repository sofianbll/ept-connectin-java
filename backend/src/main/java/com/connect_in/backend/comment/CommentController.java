package com.connect_in.backend.comment;

import com.connect_in.backend.comment.dto.CommentRequest;
import com.connect_in.backend.comment.dto.CommentResponse;
import com.connect_in.backend.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // GET /api/posts/{postId}/comments returns a paged list.
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<Page<CommentResponse>> getCommentsByPost(
            @PathVariable Long postId,
            Pageable pageable,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId, pageable, currentUser));
    }

    // POST /api/posts/{postId}/comments creates a comment.
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(postId, request, currentUser));
    }

    // PUT /api/comments/{id} updates a comment.
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(commentService.updateComment(id, request, currentUser));
    }

    // DELETE /api/comments/{id} deletes a comment.
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        commentService.deleteComment(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
