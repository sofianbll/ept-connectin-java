package com.connect_in.backend.service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    public Page<LikeResponse> toggleLike(Long postId, User currentUser) {
        return likeRepository.findByUserAndPost(User user, Post post);
            .map(like -> likeToResponse(like, currentUser));
    }

}