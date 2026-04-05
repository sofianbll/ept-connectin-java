package com.connect_in.backend.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByTeamIsNullOrderByCreatedAtDesc();

    List<Post> findAllByTeamIsNullOrTeam_IdInOrderByCreatedAtDesc(List<Long> teamIds);

    List<Post> findAllByTeam_IdOrderByCreatedAtDesc(Long teamId);

    List<Post> findAllByUserId(Long userId);

    void deleteByUserId(Long userId);

    // Detach posts from a team before team deletion.
    @Modifying
    @Query("UPDATE Post p SET p.team = null WHERE p.team.id = :teamId")
    void detachFromTeam(@Param("teamId") Long teamId);
}
