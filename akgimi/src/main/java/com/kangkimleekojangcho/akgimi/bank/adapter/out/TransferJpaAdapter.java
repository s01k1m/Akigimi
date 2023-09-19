package com.kangkimleekojangcho.akgimi.bank.adapter.out;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandTransferDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TransferJpaAdapter implements CommandTransferDbPort {

    private final TransferJpaRepository transferJpaRepository;
    @Override
    public Transfer save(Transfer transfer) {
        return transferJpaRepository.save(transfer);
    }
}
