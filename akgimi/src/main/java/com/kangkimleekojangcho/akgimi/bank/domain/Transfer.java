package com.kangkimleekojangcho.akgimi.bank.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transfer extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @ManyToOne
    private Account sendAccount;

    @ManyToOne
    private Account receiveAccount;

    private LocalDateTime transferDateTime;

    private Long sendAccountBalance;

    private Long receiveAccountBalance;
}
