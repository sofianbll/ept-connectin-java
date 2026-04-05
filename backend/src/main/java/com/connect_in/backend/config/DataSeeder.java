package com.connect_in.backend.config;

import com.connect_in.backend.comment.Comment;
import com.connect_in.backend.comment.CommentRepository;
import com.connect_in.backend.like.Like;
import com.connect_in.backend.like.LikeRepository;
import com.connect_in.backend.post.Post;
import com.connect_in.backend.post.PostRepository;
import com.connect_in.backend.team.Team;
import com.connect_in.backend.team.TeamMember;
import com.connect_in.backend.team.TeamMemberRepository;
import com.connect_in.backend.team.TeamRepository;
import com.connect_in.backend.user.Role;
import com.connect_in.backend.user.User;
import com.connect_in.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Database already seeded, skipping.");
            return;
        }

        log.info("Seeding database...");

        // ── Users ──
        User sofian = userRepository.save(User.builder()
                .email("sofian@test.com").firstName("Sofian").lastName("Test")
                .password(passwordEncoder.encode("password123")).role(Role.USER).build());

        User alice = userRepository.save(User.builder()
                .email("alice@test.com").firstName("Alice").lastName("Martin")
                .password(passwordEncoder.encode("password123")).role(Role.USER).build());

        User bob = userRepository.save(User.builder()
                .email("bob@test.com").firstName("Bob").lastName("Dupont")
                .password(passwordEncoder.encode("password123")).role(Role.USER).build());

        User shanisya = userRepository.save(User.builder()
                .email("shanisya@test.com").firstName("Shanisya").lastName("User")
                .password(passwordEncoder.encode("password123")).role(Role.USER).build());

        // ── Teams ──
        Team teamAlpha = teamRepository.save(Team.builder()
                .name("Team Alpha").description("Équipe de développement backend").owner(sofian).build());

        Team teamBeta = teamRepository.save(Team.builder()
                .name("Team Beta").description("Équipe design UI/UX").owner(shanisya).build());

        // ── Team Members ──
        // Team Alpha: Sofian (admin), Alice (member), Bob (member)
        teamMemberRepository.save(TeamMember.builder().team(teamAlpha).user(sofian).role("admin").build());
        teamMemberRepository.save(TeamMember.builder().team(teamAlpha).user(alice).role("member").build());
        teamMemberRepository.save(TeamMember.builder().team(teamAlpha).user(bob).role("member").build());

        // Team Beta: Shanisya (admin), Alice (member), Bob (member)
        teamMemberRepository.save(TeamMember.builder().team(teamBeta).user(shanisya).role("admin").build());
        teamMemberRepository.save(TeamMember.builder().team(teamBeta).user(alice).role("member").build());
        teamMemberRepository.save(TeamMember.builder().team(teamBeta).user(bob).role("member").build());

        // ── Posts ──
        Post post1 = postRepository.save(Post.builder()
                .captions("Bienvenue sur Connect'In v2 !").user(sofian).build());

        Post post2 = postRepository.save(Post.builder()
                .captions("Update sur le sprint 12").user(sofian).team(teamAlpha).build());

        Post post3 = postRepository.save(Post.builder()
                .captions("Maquettes Figma dispo").user(shanisya).team(teamBeta).build());

        Post post4 = postRepository.save(Post.builder()
                .captions("Qui veut faire un café ?").user(bob).build());

        Post post5 = postRepository.save(Post.builder()
                .captions("Review du nouveau design system").user(alice).team(teamAlpha).build());

        Post post6 = postRepository.save(Post.builder()
                .captions("Premier post de Shanisya !").user(shanisya).build());

        // ── Comments ──
        commentRepository.save(Comment.builder().text("Super projet !").user(alice).post(post1).build());
        commentRepository.save(Comment.builder().text("Merci !").user(bob).post(post1).build());
        commentRepository.save(Comment.builder().text("Bienvenue !").user(shanisya).post(post1).build());
        commentRepository.save(Comment.builder().text("Bien avancé le sprint").user(alice).post(post2).build());
        commentRepository.save(Comment.builder().text("J'ai regardé, c'est clean").user(bob).post(post3).build());
        commentRepository.save(Comment.builder().text("Moi je veux bien ☕").user(sofian).post(post4).build());
        commentRepository.save(Comment.builder().text("Le nouveau système est top").user(sofian).post(post5).build());
        commentRepository.save(Comment.builder().text("Merci pour le feedback").user(alice).post(post5).build());
        commentRepository.save(Comment.builder().text("Hâte de voir la suite").user(bob).post(post1).build());

        // ── Likes ──
        likeRepository.save(Like.builder().user(sofian).post(post1).build());
        likeRepository.save(Like.builder().user(alice).post(post1).build());
        likeRepository.save(Like.builder().user(bob).post(post1).build());
        likeRepository.save(Like.builder().user(shanisya).post(post1).build());
        likeRepository.save(Like.builder().user(alice).post(post2).build());
        likeRepository.save(Like.builder().user(bob).post(post3).build());
        likeRepository.save(Like.builder().user(alice).post(post5).build());
        likeRepository.save(Like.builder().user(sofian).post(post6).build());

        log.info("Database seeded: 4 users, 2 teams, 6 posts, 9 comments, 8 likes.");
    }
}
