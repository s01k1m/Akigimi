package com.kangkimleekojangcho.akgimi.bank.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne
    //@JoinColumn(name = "user_id")
    //private User user;

    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private Bank bank;

    private String accountNumber;

    private Long balance;

    private String password;

    private Boolean isDeleted;

    private Boolean isPasswordRegistered;

}
