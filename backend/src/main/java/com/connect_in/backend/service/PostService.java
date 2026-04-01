package com.connect_in.backend.service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Page<PostResponse> getAllPosts(Pageable pageable, User currentUser) {
        return postRepository.findAllByOrderByCreatedAtDes(pageable)
                .map(post -> mapToResponse(post, currentUser));
    }

    public PostResponse getPostById(Long id, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post n'as pas trouvé"));
        return mapToResponse(post, currentUser);
    }

    @Transactional
    public createPost(PostRequestDTO, User currentUser) {
        // to save and to return here the create section (response)

    }

    @Transactional
    public updatePost(Long id, UpdatePostRequest request) {
        log.info("Mise à jour de le post avec l'ID : {}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post non trouvé avec l'ID : " + id));

        if (request.getPost() != null) {
            if (postRepository.existsby)
        }

    }

    @Transactional
    public void deletePost(Long id) {
        log.info("Suppression du Post avec l'ID : {}", id);

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post non trouvé avec l'ID : " + id);
        }

        userRepository.deleteById(id);
        log.info("Post supprimé avec succès");
    }

}


