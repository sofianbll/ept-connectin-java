package com.connect_in.backend.repository;

import com.connect_in.backend.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
        // Retrieves all posts sorted by creation date (newest first), with pagination support
        // Spring auto-generates the SQL: SELECT * FROM posts ORDER BY created_at DESC LIMIT ? OFFSET ?
}