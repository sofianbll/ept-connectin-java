package com.connect_in.backend.like;

import com.connect_in.backend.like.dto.LikeResponse;
import com.connect_in.backend.post.Post;
import com.connect_in.backend.post.PostRepository;
import com.connect_in.backend.shared.exception.ResourceNotFoundException;
import com.connect_in.backend.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    // Toggle : like si pas encore liké, unlike si déjà liké
    @Transactional
    public LikeResponse toggleLike(Long postId, User currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post non trouvé avec l'ID : " + postId));

        boolean alreadyLiked = likeRepository.existsByPostIdAndUserId(postId, currentUser.getId());

        if (alreadyLiked) {
            likeRepository.deleteByPostIdAndUserId(postId, currentUser.getId());
            log.info("Post unliké, postId={}, userId={}", postId, currentUser.getId());
        } else {
            Like like = Like.builder()
                    .post(post)
                    .user(currentUser)
                    .build();
            likeRepository.save(like);
            log.info("Post liké, postId={}, userId={}", postId, currentUser.getId());
        }

        boolean nowLiked = !alreadyLiked;
        int likesCount = likeRepository.countByPostId(postId);
        return new LikeResponse(nowLiked, likesCount);
    }
}
