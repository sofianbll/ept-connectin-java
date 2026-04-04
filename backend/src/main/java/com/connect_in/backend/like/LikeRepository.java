package com.connect_in.backend.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);   // boolean
    void deleteByPostIdAndUserId(Long postId, Long userId);   // (optional) pour supprimer
    int countByPostId(Long postId);   // Long (pour likes_count)
}
