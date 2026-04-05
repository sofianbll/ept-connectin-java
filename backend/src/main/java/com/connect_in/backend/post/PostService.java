package com.connect_in.backend.post;

import com.connect_in.backend.comment.CommentRepository;
import com.connect_in.backend.comment.dto.CommentResponse;
import com.connect_in.backend.like.LikeRepository;
import com.connect_in.backend.post.dto.PostResponse;
import com.connect_in.backend.shared.exception.ResourceNotFoundException;
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

    @Value("${storage.posts-dir:storage/posts}")
    private String postsStorageDir;

    // GET /api/posts — filtre par teamId si fourni, sinon tous les posts
    public List<PostResponse> getAllPosts(Long teamId, User currentUser) {
        List<Post> posts = (teamId != null)
                ? postRepository.findAllByTeamIdOrderByCreatedAtDesc(teamId)
                : postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream()
                .map(post -> mapToResponse(post, currentUser))
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long id, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post non trouvé avec l'ID : " + id));
        return mapToResponse(post, currentUser);
    }

    @Transactional
    public PostResponse createPost(String captions, MultipartFile image, Long teamId, User currentUser) {
        String imagePath = saveImage(image);

        Post post = Post.builder()
                .captions(captions)
                .imagePath(imagePath)
                .teamId(teamId)
                .user(currentUser)
                .build();

        Post saved = postRepository.save(post);
        log.info("Post créé avec l'ID : {}", saved.getId());
        return mapToResponse(saved, currentUser);
    }

    @Transactional
    public PostResponse updatePost(Long id, String captions, MultipartFile image, Long teamId, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post non trouvé avec l'ID : " + id));

        if (captions != null && !captions.isBlank()) {
            post.setCaptions(captions);
        }
        if (image != null && !image.isEmpty()) {
            post.setImagePath(saveImage(image));
        }
        if (teamId != null) {
            post.setTeamId(teamId);
        }

        Post updated = postRepository.save(post);
        log.info("Post mis à jour avec l'ID : {}", updated.getId());
        return mapToResponse(updated, currentUser);
    }

    @Transactional
    public void deletePost(Long id, User currentUser) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post non trouvé avec l'ID : " + id);
        }
        postRepository.deleteById(id);
        log.info("Post supprimé avec l'ID : {}", id);
    }

    // --- Helpers ---

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

            // Retourner le chemin accessible via /storage/posts/filename
            return "/storage/posts/" + filename;
        } catch (IOException e) {
            log.error("Erreur lors de la sauvegarde de l'image : {}", e.getMessage());
            throw new RuntimeException("Impossible de sauvegarder l'image", e);
        }
    }
}
