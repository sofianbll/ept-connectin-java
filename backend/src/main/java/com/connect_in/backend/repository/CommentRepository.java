package com.connect_in.backend.repository;

import com.connect_in.backend.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springfaremwork.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAll(Pageable pageable);
        // retrieves all comments sorted by creation date (newest first), with pagination support
        // spring auto-generates the SQL: SELECT * FROM comments ORDER BY created_at DESC LIMIT ? OFFSET ?

}