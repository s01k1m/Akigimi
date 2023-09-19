package com.kangkimleekojangcho.akgimi.sns.domain;


import com.kangkimleekojangcho.akgimi.bank.domain.BaseTimeEntity;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="feed")
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Lob
    private String content;

    private String place;

    private Long price;

    private String notPurchasedItem;

    private Long accumulatedAmount;

    private Boolean isPublic;

    private Boolean isDeleted;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @JoinColumn(name="challenge_id")
    @ManyToOne
    private Challenge challenge;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY)
    private List<Like> like;
}
