package com.connect_in.backend.controller;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/posts/{post_id}")
    public ResponseEntity<CommentResponse> getCommentsByPost(Long postId) {
        return p
    }

}