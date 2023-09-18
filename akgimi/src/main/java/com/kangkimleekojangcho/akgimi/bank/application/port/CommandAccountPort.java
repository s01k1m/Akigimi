package com.kangkimleekojangcho.akgimi.bank.application.port;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;

public interface CommandAccountPort {
    Account add(Account account);
}
