package com.connect_in.backend.service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Page<PostResponse> getAllPosts(Pageable pageable, User currentUser) {
        return postRepository
                .findAllByOrder(pageable)
                .map(post -> mapToResponse(post, currentUser));
    }

    public PostResponse getPostById(Long id, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found here 404 (error)"));
        return mapToResponse(post, currentUser);
    }

    public createPost() {

    }

    public updatePost() {

    }

    public deletePost()

}


