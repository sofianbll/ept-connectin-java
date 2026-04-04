package com.connect_in.backend.post;

import com.connect_in.backend.post.dto.PostRequest;
import com.connect_in.backend.post.dto.PostResponse;
import com.connect_in.backend.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<PostResponse> getAllPosts(Pageable pageable, User currentUser) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(post -> mapToResponse(post, currentUser));
    }

    public PostResponse getPostById(Long id, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post n'as pas trouvé avec l'ID : " + id));
        return mapToResponse(post, currentUser);
    }

    @Transactional
    public PostResponse createPost(PostRequest request, User currentUser) {
        Post post = Post.builder()
                .captions(request.getCaptions())
                .imagePath(request.getImagePath())
                .teamId(request.getTeamId())
                .user(currentUser)
                .build();

        Post saved = postRepository.save(post);
        log.info("Post a bien créé avec l'ID : {}", saved.getId());
        return mapToResponse(saved, currentUser);
    }

    @Transactional
    public PostResponse updatePost(Long id, PostRequest request, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post non trouvé avec l'ID : " + id));
        post.setCaptions(request.getCaptions());
        post.setImagePath(request.getImagePath());
        post.setTeamId(request.getTeamId());

        Post updated = postRepository.save(post);
        log.info("Mis à jour avec l'ID : {}", updated.getId());
        return mapToResponse(updated, currentUser);
    }

    @Transactional
    public void deletePost(Long id, User currentUser) {

        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post non trouvé avec l'ID : " + id);
        }

        postRepository.deleteById(id);
        log.info("Post supprimé avec ID : {}", id);
    }

// add more

    private PostResponse mapToResponse(Post post, User currentUser) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setCaptions(post.getCaptions());
        response.setImagePath(post.getImagePath());
        response.setCreatedAt(post.getCreatedAt());
        response.setLikesCount(0);
        response.setLiked(false);
        response.setCommentsCount(0);
        return response;
    }
}
