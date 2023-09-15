package com.kangkimleekojangcho.akgimi.user.application.response;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class GenerateWithdrawalAccountServiceResponse {
    private final String bank;
    private final String account;

    private GenerateWithdrawalAccountServiceResponse(String bank, String account) {
        this.bank = bank;
        this.account = account;
    }

    public static GenerateWithdrawalAccountServiceResponse from(Account account) {
        return new GenerateWithdrawalAccountServiceResponse(account.getBank().name(), account.getAccountNumber());
    }
}
