package com.kangkimleekojangcho.akgimi.sns.domain;

import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name="likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @JoinColumn(name="feed_id")
    @ManyToOne
    private Feed feed;

    @JoinColumn(name="user_id")
    @ManyToOne
    private User user;
}
