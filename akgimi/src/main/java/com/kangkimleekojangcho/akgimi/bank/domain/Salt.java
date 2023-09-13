package com.kangkimleekojangcho.akgimi.bank.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "salt")
public class Salt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String saltValue;

    @OneToOne
    private Account account;

}
