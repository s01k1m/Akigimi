package com.kangkimleekojangcho.akgimi.challenge.domain;


import com.kangkimleekojangcho.akgimi.bank.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;



@Getter
@Entity(name="postscript")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Postscript extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postscriptId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="challenge_id")
    private Challenge challenge;

    private String content;

    private boolean isDeleted;

    private String image;
}
