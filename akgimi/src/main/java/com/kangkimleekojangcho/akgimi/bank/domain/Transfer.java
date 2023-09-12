package com.kangkimleekojangcho.akgimi.bank.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account sendAccount;

    @ManyToOne
    @JoinColumn(name ="account_id")
    private Account receiveAccount;

    private String transferDateTime;
}
