package com.kangkimleekojangcho.akgimi.challenge.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne
    private Product product;

    private Integer accumulatedAccount;
    private boolean achievementState;
    private LocalDate achievementDate;
    private Integer challengePeriod;
    private LocalDate challengeStartDate;
    private LocalDate challengeEndDate;
    private boolean isInProgress;
}
