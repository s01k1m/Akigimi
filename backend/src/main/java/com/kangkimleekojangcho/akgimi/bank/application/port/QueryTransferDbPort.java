package com.kangkimleekojangcho.akgimi.bank.application.port;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.Transfer;

import java.util.List;
import java.util.Optional;

public interface QueryTransferDbPort {
    List<Transfer> findByAccount(Account account);
}
