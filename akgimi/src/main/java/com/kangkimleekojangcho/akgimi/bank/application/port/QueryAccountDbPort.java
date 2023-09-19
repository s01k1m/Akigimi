package com.kangkimleekojangcho.akgimi.bank.application.port;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.user.domain.User;

import java.util.Optional;

public interface QueryAccountDbPort {

    Optional<Account> findAccountByAccountTypeAndUserId(AccountType deposit, Long userId);

    Optional<Account> findById(Long accountId);

    Optional<Account> findByUserAndAccountType(User user, AccountType accountType);

    Optional<Account> findByAccountNumber(String accountNumber, AccountType accountType);

    Optional<Account> findByUserAndAccountTypeAndAccountNumber(User user, AccountType accountType, String accountNumber);
}
