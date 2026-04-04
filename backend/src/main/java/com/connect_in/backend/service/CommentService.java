package com.connect_in.backend.service;

import com.connect_in.backend.model.dto.request.CommentRequest;
import com.connect_in.backend.model.dto.response.CommentResponse;
import com.connect_in.backend.model.entity.Comment;
import com.connect_in.backend.model.entity.Post;
import com.connect_in.backend.model.entity.User;
import com.connect_in.backend.repository.CommentRepository;
import com.connect_in.backend.repository.PostRepository;
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

    public Page<CommentResponse> getCommentsByPost(Long postId, Pageable pageable, User currentUser) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId, pageable)
                .map(comment -> mapToResponse(comment, currentUser));
    }

    public CommentResponse getCommentsById(Long commentId, User currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment n'as pas trouvé avec l'ID :" + commentId));
        return mapToResponse(comment, currentUser);
    }

    @Transactional
    public CommentResponse createComment(Long postId, CommentRequest request, User currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post non trouvé avec l'ID : " + postId));

        Comment comment = Comment.builder()
                .text(request.getText())
                .post(post)
                .user(currentUser)
                .build();

        Comment saved = commentRepository.save(comment);
            log.info("Comment crée avec l'ID : {}",  saved.getId());
            return mapToResponse(saved, currentUser);
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, CommentRequest request, User currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment non trouvé avec l'ID : " + commentId));
        comment.setText(request.getText());

        Comment updated = commentRepository.save(comment);
            log.info("Le comment mis à jour avec l'ID : {}",  updated.getId());
            return mapToResponse(updated, currentUser);
    }

    @Transactional
    public void deleteComment(Long commentId, User currentUser) {
        if(!commentRepository.existsById(commentId)) {
            throw new RuntimeException("Comment non trouvé avec l'ID : " + commentId);
        }

        commentRepository.deleteById(commentId);
            log.info("Comment supprimé avec l'ID : {}",  commentId);
    }


    private CommentResponse mapToResponse(Comment comment, User currentUser) {
        CommentResponse response = new CommentResponse();
            response.setId(comment.getId());
            response.setText(comment.getText());
            response.setCreatedAt((comment.getCreatedAt()));
            return response;
    }

}
