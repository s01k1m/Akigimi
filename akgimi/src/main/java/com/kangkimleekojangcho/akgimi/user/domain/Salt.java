package com.kangkimleekojangcho.akgimi.user.domain;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Salt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String saltValue;

    @Enumerated(value = EnumType.STRING)
    private SaltType type;

    public Salt(String saltValue, SaltType type) {
        this.saltValue = saltValue;
        this.type = type;
    }
}
