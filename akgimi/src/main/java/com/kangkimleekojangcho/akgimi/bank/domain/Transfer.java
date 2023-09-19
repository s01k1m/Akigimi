package com.kangkimleekojangcho.akgimi.bank.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
public class Transfer {

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
