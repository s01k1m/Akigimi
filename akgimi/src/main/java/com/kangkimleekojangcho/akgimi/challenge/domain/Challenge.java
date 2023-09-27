package com.kangkimleekojangcho.akgimi.challenge.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kangkimleekojangcho.akgimi.bank.domain.BaseTimeEntity;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    private Product product;

    private Long accumulatedAmount;
    private boolean achievementState;
    private LocalDate achievementDate;
    private Integer challengePeriod;
    private LocalDate challengeStartDate;
    private LocalDate challengeEndDate;
    private boolean isInProgress;

    public int calculatePercentage(long balance){
        int productPrice = this.getProduct().getPrice();
        int percentage = (int)Math.round(((double)balance/productPrice)*100);
        if (percentage > 100) percentage = 100;
        return percentage;
    }

    public CharacterStatus getCharacterStatusByPercentage(int percentage){
        if (percentage < 20) return CharacterStatus.LEVEL0;
        if (percentage < 40) return CharacterStatus.LEVEL1;
        if (percentage < 60) return CharacterStatus.LEVEL2;
        if (percentage < 80) return CharacterStatus.LEVEL3;
        if (percentage < 100) return CharacterStatus.LEVEL4;
        return CharacterStatus.LEVEL5;
    }

    public int calculateDays(LocalDate today){
        return this.challengeStartDate.until(today).getDays()+1;
    }

    public void update(
            @Nullable Boolean achievementState,
            @Nullable LocalDate achievementDate,
            @Nullable Boolean isInProgress
    ){
        if(achievementState != null){
            this.achievementState = achievementState;
        }
        if(achievementDate != null){
            this.achievementDate = achievementDate;
        }
        if(isInProgress != null){
            this.isInProgress = isInProgress;
        }
    }
}
