package com.kangkimleekojangcho.akgimi.bank.domain;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "account")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private Bank bank;

    private String accountNumber;

    private Long balance;

    private String password;

    private Boolean isDeleted;

    private Boolean isPasswordRegistered;

    public Long withdraw(Long withdrawalAmount) {
        Long result = balance-withdrawalAmount;
        if(result<0) throw new BadRequestException(BadRequestExceptionCode.LACK_OF_ACCOUNT_BALANCE);
        balance = result;
        return result;
    }

    public void deposit(Long remain) {
        Long result = balance + remain;
        if(result<0) throw new BadRequestException(BadRequestExceptionCode.EXCEED_MAXIMUM_BALANCE);
        balance = result;
    }

    public void setPassword(String password, boolean isPasswordRegistered) {
        this.password = password;
        this.isPasswordRegistered = isPasswordRegistered;
    }
}
