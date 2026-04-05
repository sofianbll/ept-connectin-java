package com.connect_in.backend.post;

import com.connect_in.backend.comment.CommentRepository;
import com.connect_in.backend.like.LikeRepository;
import com.connect_in.backend.shared.exception.ForbiddenException;
import com.connect_in.backend.team.Team;
import com.connect_in.backend.team.TeamMemberRepository;
import com.connect_in.backend.team.TeamRepository;
import com.connect_in.backend.user.User;
import com.connect_in.backend.user.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private MultipartFile image;

    @InjectMocks
    private PostService postService;

    @Test
    void createPostThrowsWhenUserNotTeamMember() {
        User owner = User.builder().id(1L).build();
        User requester = User.builder().id(2L).build();
        Team team = Team.builder().id(10L).owner(owner).build();

        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));
        when(teamMemberRepository.existsByTeamIdAndUserId(10L, 2L)).thenReturn(false);
        when(image.isEmpty()).thenReturn(true);

        Assertions.assertThrows(ForbiddenException.class,
                () -> postService.createPost("caption", image, 10L, requester));
    }

    @Test
    void getAllPostsWithTeamIdThrowsWhenUserNotMember() {
        User owner = User.builder().id(1L).build();
        User requester = User.builder().id(2L).build();
        Team team = Team.builder().id(10L).owner(owner).build();

        when(teamRepository.findById(10L)).thenReturn(Optional.of(team));
        when(teamMemberRepository.existsByTeamIdAndUserId(10L, 2L)).thenReturn(false);

        Assertions.assertThrows(ForbiddenException.class,
                () -> postService.getAllPosts(10L, requester));
    }

    @Test
    void getPostByIdThrowsWhenUserNotMember() {
        User owner = User.builder().id(1L).build();
        User requester = User.builder().id(2L).build();
        Team team = Team.builder().id(10L).owner(owner).build();
        Post post = Post.builder().id(5L).team(team).user(owner).build();

        when(postRepository.findById(5L)).thenReturn(Optional.of(post));
        when(teamMemberRepository.existsByTeamIdAndUserId(10L, 2L)).thenReturn(false);

        Assertions.assertThrows(ForbiddenException.class,
                () -> postService.getPostById(5L, requester));
    }

    @Test
    void getAllPostsWithoutTeamIdFiltersToPublicWhenNoMemberships() {
        User requester = User.builder().id(2L).build();

        when(teamMemberRepository.findByUserId(2L)).thenReturn(List.of());
        when(postRepository.findAllByTeamIsNullOrderByCreatedAtDesc()).thenReturn(List.of());

        postService.getAllPosts(null, requester);

        verify(postRepository).findAllByTeamIsNullOrderByCreatedAtDesc();
    }

    @Test
    void getAllPostsWithoutTeamIdIncludesMemberTeams() {
        User requester = User.builder().id(2L).build();
        Team team = Team.builder().id(10L).build();
        com.connect_in.backend.team.TeamMember membership = com.connect_in.backend.team.TeamMember.builder()
                .team(team)
                .user(requester)
                .role("member")
                .build();

        when(teamMemberRepository.findByUserId(2L)).thenReturn(List.of(membership));
        when(postRepository.findAllByTeamIsNullOrTeam_IdInOrderByCreatedAtDesc(List.of(10L)))
                .thenReturn(List.of());

        postService.getAllPosts(null, requester);

        verify(postRepository).findAllByTeamIsNullOrTeam_IdInOrderByCreatedAtDesc(List.of(10L));
    }
}
