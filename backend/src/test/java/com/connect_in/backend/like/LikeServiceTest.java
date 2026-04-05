package com.connect_in.backend.like;

import com.connect_in.backend.like.dto.LikeResponse;
import com.connect_in.backend.post.Post;
import com.connect_in.backend.post.PostRepository;
import com.connect_in.backend.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikeService likeService;

    @Test
    void toggleLikeReturnsLikedTrueWhenNotLiked() {
        Post post = Post.builder().id(1L).build();
        User user = User.builder().id(10L).build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(likeRepository.existsByPostIdAndUserId(1L, 10L)).thenReturn(false);
        when(likeRepository.countByPostId(1L)).thenReturn(1);

        LikeResponse response = likeService.toggleLike(1L, user);

        assertTrue(response.isLiked());
    }
}
