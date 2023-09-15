package com.kangkimleekojangcho.akgimi.bank.adapter.out;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountPort;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserDbPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountJpaAdapter implements CommandAccountPort {
    private final AccountJpaRepository accountJpaRepository;
}
