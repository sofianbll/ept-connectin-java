package com.connect_in.backend.service;

import com.connect_in.backend.model.dto.request.LikeRequest;
import com.connect_in.backend.model.dto.response.LikeResponse;
import com.connect_in.backend.model.entity.Like;
import com.connect_in.backend.model.entity.User;
import com.connect_in.backend.model.entity.Post;
import com.connect_in.backend.repository.LikeRepository;
import com.connect_in.backend.repository.PostRepository;
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

    @Transactional
    public LikeResponse likePost(LikeRequest request, User currentUser) {
        if (likeRepository.existsByPostIdAndUserId(request.getPostId(), currentUser.getId())) {
            throw new RuntimeException("Post déjà liked !");
        }
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post n'as pas trouvé avec l'ID : " + request.getPostId()));
                Like like = Like.builder()
                        .post(post)
                        .user(currentUser)
                        .build();
                Like saved = likeRepository.save(like);
                    log.info("Post liké succès, ID : {}", saved.getId());
                    return mapToResponse(saved);
    }

    @Transactional
    public void unlikePost(Long postId, User currentUser) {
        if (!likeRepository.existsByPostIdAndUserId(postId, currentUser.getId())) {
            throw new RuntimeException("Like non trouvé");
        }
        likeRepository.deleteByPostIdAndUserId(postId, currentUser.getId());
            log.info("Post unliké succès, Id : {}", postId);
    }

    private LikeResponse mapToResponse(Like like) {
        LikeResponse response = new LikeResponse();
            response.setId(like.getId());
            response.setPostId(like.getPost().getId());
            response.setUserId(like.getUser().getId());
            return response;
    }

}