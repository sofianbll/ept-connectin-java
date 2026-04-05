package com.connect_in.backend.post;

import com.connect_in.backend.comment.CommentRepository;
import com.connect_in.backend.comment.dto.CommentResponse;
import com.connect_in.backend.like.LikeRepository;
import com.connect_in.backend.post.dto.PostResponse;
import com.connect_in.backend.shared.exception.ResourceNotFoundException;
import com.connect_in.backend.team.Team;
import com.connect_in.backend.team.TeamRepository;
import com.connect_in.backend.user.User;
import com.connect_in.backend.user.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final UserMapper userMapper;
    private final TeamRepository teamRepository;

    @Value("${storage.posts-dir:storage/posts}")
    private String postsStorageDir;

    // GET /api/posts filters by teamId when provided.
    public List<PostResponse> getAllPosts(Long teamId, User currentUser) {
        List<Post> posts = (teamId != null)
                ? postRepository.findAllByTeam_IdOrderByCreatedAtDesc(teamId)
                : postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream()
                .map(post -> mapToResponse(post, currentUser))
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long id, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id));
        return mapToResponse(post, currentUser);
    }

    @Transactional
    public PostResponse createPost(String captions, MultipartFile image, Long teamId, User currentUser) {
        String imagePath = saveImage(image);

        Team team = null;
        if (teamId != null) {
            team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));
        }

        Post post = Post.builder()
                .captions(captions)
                .imagePath(imagePath)
                .team(team)
                .user(currentUser)
                .build();

        Post saved = postRepository.save(post);
        log.info("Post created with ID: {}", saved.getId());
        return mapToResponse(saved, currentUser);
    }

    @Transactional
    public PostResponse updatePost(Long id, String captions, MultipartFile image, Long teamId, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id));

        if (captions != null && !captions.isBlank()) {
            post.setCaptions(captions);
        }
        if (image != null && !image.isEmpty()) {
            post.setImagePath(saveImage(image));
        }
        if (teamId != null) {
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));
            post.setTeam(team);
        }

        Post updated = postRepository.save(post);
        log.info("Post updated with ID: {}", updated.getId());
        return mapToResponse(updated, currentUser);
    }

    @Transactional
    public void deletePost(Long id, User currentUser) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with ID: " + id);
        }
        postRepository.deleteById(id);
        log.info("Post deleted with ID: {}", id);
    }

    // Helpers

    private PostResponse mapToResponse(Post post, User currentUser) {
        boolean liked = likeRepository.existsByPostIdAndUserId(post.getId(), currentUser.getId());
        int likesCount = likeRepository.countByPostId(post.getId());
        long commentsCount = commentRepository.countByPostId(post.getId());

        List<CommentResponse> comments = commentRepository
                .findByPostIdOrderByCreatedAtAsc(post.getId())
                .stream()
                .map(comment -> {
                    CommentResponse cr = new CommentResponse();
                    cr.setId(comment.getId());
                    cr.setText(comment.getText());
                    cr.setCreatedAt(comment.getCreatedAt());
                    cr.setUser(userMapper.toDTO(comment.getUser()));
                    return cr;
                })
                .collect(Collectors.toList());

        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setCaptions(post.getCaptions());
        response.setImagePath(post.getImagePath());
        response.setUser(userMapper.toDTO(post.getUser()));
        response.setLiked(liked);
        response.setLikesCount(likesCount);
        response.setCommentsCount((int) commentsCount);
        response.setComments(comments);
        response.setCreatedAt(post.getCreatedAt());
        return response;
    }

    private String saveImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return null;
        }
        try {
            Path dir = Paths.get(postsStorageDir);
            Files.createDirectories(dir);

            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = dir.resolve(filename);
            Files.write(filePath, image.getBytes());

            // Return a public path under /storage/posts.
            return "/storage/posts/" + filename;
        } catch (IOException e) {
            log.error("Failed to save image: {}", e.getMessage());
            throw new RuntimeException("Failed to save image.", e);
        }
    }
}
