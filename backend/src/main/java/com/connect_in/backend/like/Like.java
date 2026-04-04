package com.connect_in.backend.like;

import com.connect_in.backend.post.Post;
import com.connect_in.backend.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // java JPA, how to store the actual object
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) // java JPA, how to store the actual object
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
