package com.kangkimleekojangcho.akgimi.user.domain;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Salt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    @Column(nullable = false)
    private String saltValue;

    @Enumerated(value = EnumType.STRING)
    private SaltType type;

    public Salt(User user, String saltValue, SaltType type) {
        this.user = user;
        this.saltValue = saltValue;
        this.type = type;
    }
}
