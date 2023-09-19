package com.kangkimleekojangcho.akgimi.bank.application.port;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;

public interface CommandAccountDbPort {

    Account save(Account account);

}
