package com.kangkimleekojangcho.akgimi.bank.application.port;

import com.kangkimleekojangcho.akgimi.bank.domain.Transfer;


public interface CommandTransferDbPort {

    Transfer save(Transfer transfer);
}
