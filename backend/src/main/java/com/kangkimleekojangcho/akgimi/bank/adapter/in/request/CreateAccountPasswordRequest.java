package com.kangkimleekojangcho.akgimi.bank.adapter.in.request;

import com.kangkimleekojangcho.akgimi.bank.application.request.CreateAccountPasswordServiceRequest;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class CreateAccountPasswordRequest {
    private String bank;
    private String accountType;
    private String accountNumber;
    private String password;

    public CreateAccountPasswordServiceRequest toServiceRequest() {
        Bank bankObj = createBankObject();
        AccountType accountTypeObj = createAccountTypeObject();
        return new CreateAccountPasswordServiceRequest(bankObj,accountTypeObj,accountNumber,password);
    }

    private AccountType createAccountTypeObject() {
        AccountType accountTypeObj;
        try{
            accountTypeObj = AccountType.valueOf(accountType);
        } catch (IllegalArgumentException e){
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "유효하지 않은 계좌 유형입니다.");
        }
        return accountTypeObj;
    }

    private Bank createBankObject() {
        Bank bankObj;
        try{
            bankObj = Bank.valueOf(bank);
        } catch (IllegalArgumentException e){
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "유효하지 않은 은행값입니다.");
        }
        return bankObj;
    }
}
