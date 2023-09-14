package com.kangkimleekojangcho.akgimi.sns.domain;


import com.kangkimleekojangcho.akgimi.bank.domain.BaseTimeEntity;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

//    @JoinColumn(name="challenge_id")
//    @ManyToOne
//    private Challenge challenge;


    @Builder
    public Feed(String content, String place, Long price, String notPurchasedItem,
                Long accumulatedAmount, Boolean isPublic, Boolean isDeleted) {
        this.content = content;
        this.place = place;
        this.price = price;
        this.notPurchasedItem = notPurchasedItem;
        this.accumulatedAmount = accumulatedAmount;
        this.isPublic = isPublic;
        this.isDeleted = isDeleted;
    }
}
