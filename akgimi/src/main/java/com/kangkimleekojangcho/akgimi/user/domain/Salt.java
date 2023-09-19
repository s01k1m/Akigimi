package com.kangkimleekojangcho.akgimi.user.domain;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import jakarta.persistence.*;
import lombok.Builder;

@Entity
public class Salt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long accountId;
    @Column(nullable = false)
    private String saltValue;

    public Salt(Account account, String saltValue) {
        this.accountId = account.getId();
        this.saltValue = saltValue;
    }
}
