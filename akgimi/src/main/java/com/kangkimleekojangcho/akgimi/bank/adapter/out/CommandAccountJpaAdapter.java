package com.kangkimleekojangcho.akgimi.bank.adapter.out;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandAccountJpaAdapter implements CommandAccountDbPort {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account save(Account account) {
        return accountJpaRepository.save(account);
    }
}
