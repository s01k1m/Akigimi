package com.kangkimleekojangcho.akgimi.bank.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transfer extends BaseTimeEntity implements Comparable<Transfer> {

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


    @Override
    public int compareTo(Transfer transfer) {
        if(transfer.transferDateTime.isBefore(transferDateTime)){
            return -1;
        }else if(transfer.transferDateTime.isAfter(transferDateTime)){
            return 1;
        }
        return 0;
    }
}
