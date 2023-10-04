package com.kangkimleekojangcho.akgimi.sns.domain;

import jakarta.persistence.*;
import lombok.*;

@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CountLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;

    private Long likeCount;

    @Builder
    private CountLike(Long id, Feed feed, Long likeCount) {
        this.id = id;
        this.feed = feed;
        this.likeCount = likeCount;
    }

    public void setLikeCount(Long count) {
        this.likeCount = count;
    }
}
