package com.kangkimleekojangcho.akgimi.bank.adapter.out;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandTransferDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.QueryTransferDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class TransferJpaAdapter implements CommandTransferDbPort, QueryTransferDbPort {

    private final TransferJpaRepository transferJpaRepository;
    @Override
    public Transfer save(Transfer transfer) {
        return transferJpaRepository.save(transfer);
    }

    @Override
    public List<Transfer> findBySendAccountOrReceiveAccount(Account account) {
        return transferJpaRepository.findBySendAccountOrReceiveAccount(account);
    }
}
