package com.kangkimleekojangcho.akgimi.bank.application.port;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;

import java.util.Optional;

public interface QueryAccountDbPort {

    Optional<Account> findAccountByAccountTypeAndUserId(AccountType deposit, Long userId);
}
