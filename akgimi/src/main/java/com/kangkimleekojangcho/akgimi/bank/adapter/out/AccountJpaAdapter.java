package com.kangkimleekojangcho.akgimi.bank.adapter.out;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountJpaAdapter implements CommandAccountDbPort, QueryAccountDbPort {

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
}
