package com.connect_in.backend.repository;

import com.connect_in.backend.model.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Page<Like> existsByUserAndPost(User user, Post post);   // boolean
    Page<Like> findByUserAndPost(User user, Post post);   // (optional) pour supprimer
    Page<Like> countByPost(Post post);   // Long (pour likes_count)

}