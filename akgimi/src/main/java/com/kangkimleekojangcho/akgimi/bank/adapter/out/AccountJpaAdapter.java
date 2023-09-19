package com.kangkimleekojangcho.akgimi.bank.adapter.out;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountJpaAdapter implements CommandAccountDbPort, QueryAccountDbPort{
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Optional<Account> findAccountByAccountTypeAndUserId(AccountType deposit, Long userId) {
        return accountJpaRepository.findByAccountTypeAndUserId(deposit, userId);
    }

    @Override
    public Optional<Account> findById(Long accountId) {
        return accountJpaRepository.findById(accountId);
    }

    @Override
    public Account save(Account account) {
        return accountJpaRepository.save(account);
    }

    @Override
    public Optional<Account> findByUserAndAccountType(User user, AccountType accountType) {
        return accountJpaRepository.findByUserAndAccountType(user,accountType);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber, AccountType accountType) {
        return accountJpaRepository.findByAccountNumberAndAccountType(accountNumber,accountType);
    }

    @Override
    public Optional<Account> findByUserAndAccountTypeAndAccountNumber(User user, AccountType accountType, String accountNumber) {
        return accountJpaRepository.findByUserAndAccountTypeAndAccountNumber(user,accountType,accountNumber);
    }

}
