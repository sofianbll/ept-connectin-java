package com.connect_in.backend.comment;

import com.connect_in.backend.comment.dto.CommentRequest;
import com.connect_in.backend.comment.dto.CommentResponse;
import com.connect_in.backend.post.Post;
import com.connect_in.backend.post.PostRepository;
import com.connect_in.backend.shared.exception.ResourceNotFoundException;
import com.connect_in.backend.user.User;
import com.connect_in.backend.user.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserMapper userMapper;

    public Page<CommentResponse> getCommentsByPost(Long postId, Pageable pageable, User currentUser) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId, pageable)
                .map(this::mapToResponse);
    }

    public CommentResponse getCommentsById(Long commentId, User currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));
        return mapToResponse(comment);
    }

    @Transactional
    public CommentResponse createComment(Long postId, CommentRequest request, User currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));

        Comment comment = Comment.builder()
                .text(request.getText())
                .post(post)
                .user(currentUser)
                .build();

        Comment saved = commentRepository.save(comment);
        log.info("Comment created with ID: {}", saved.getId());
        return mapToResponse(saved);
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, CommentRequest request, User currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));
        comment.setText(request.getText());

        Comment updated = commentRepository.save(comment);
        log.info("Comment updated with ID: {}", updated.getId());
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteComment(Long commentId, User currentUser) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Comment not found with ID: " + commentId);
        }
        commentRepository.deleteById(commentId);
        log.info("Comment deleted with ID: {}", commentId);
    }

    private CommentResponse mapToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setText(comment.getText());
        response.setUser(userMapper.toDTO(comment.getUser()));
        response.setCreatedAt(comment.getCreatedAt());
        return response;
    }
}
