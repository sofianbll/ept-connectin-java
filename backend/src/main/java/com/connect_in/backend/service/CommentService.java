package com.connect_in.backend.service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public Page<CommentResponse> getCommentsByPost(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(Long postId)
                .map(comment -> commentToResponse(comment, currentUser));
    }

    public CommentResponse getCommentsById(Long postId) {
        Comment comment = commentRepository.findByPostId(postId)
                .orElseThrow(() -> new RuntimeException("Comment n'as pas trouvé"));
        return mapToResponse(comment, currentUser);
    }

    @Transactional
    public createComment(Long postId, CommentRequestDTO, User currentUser) {
        // here
    }

    @Transactional
    public updateComment(Long postId, Long commentId, CommentRequestDTO, User currentUser) {

    }

    @Transactional
    public deleteComment(Long postId, Long commentId, User currentUser) {
        log.info("Suppression du Comment avec l'ID : {}", postId);

        if(!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Comment non trouvé avec l'ID : " + postId);
        }

        postRepository.deleteById(commentId);
        log.info("Comment supprimé avec succès");
    }

}
