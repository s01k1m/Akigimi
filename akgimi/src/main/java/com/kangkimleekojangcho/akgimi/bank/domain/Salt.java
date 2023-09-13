package com.kangkimleekojangcho.akgimi.bank.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "salt")
public class Salt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @OneToOne
    private Account account;

}
