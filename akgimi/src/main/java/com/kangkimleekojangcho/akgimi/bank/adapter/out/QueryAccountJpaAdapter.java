package com.kangkimleekojangcho.akgimi.bank.adapter.out;

import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QueryAccountJpaAdapter implements QueryAccountDbPort {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Optional<Account> findAccountByAccountTypeAndUserId(AccountType deposit, Long userId) {
        return accountJpaRepository.findAccountByAccountTypeAndUserId(deposit, userId);
    }
}
