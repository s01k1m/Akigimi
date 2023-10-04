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
    @JoinColumn
    private Feed feed;

    private Long count;

    @Builder
    private CountLike(Long id, Feed feed, Long count) {
        this.id = id;
        this.feed = feed;
        this.count = count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
