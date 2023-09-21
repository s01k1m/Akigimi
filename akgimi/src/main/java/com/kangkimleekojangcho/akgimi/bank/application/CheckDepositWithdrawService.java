package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.QueryTransferDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.response.CheckDepositWithDrawServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.application.response.TransferResponse;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Transfer;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckDepositWithdrawService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountDbPort queryAccountDbPort;
    private final QueryTransferDbPort queryTransferDbPort;
    @Transactional(readOnly = true)
    public CheckDepositWithDrawServiceResponse checkDepositWithdraw(long userId, AccountType accountType) {
        // 1. User 정보를 가져온다.
        User user = queryUserDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        // 2. User Account를 가져온다.
        Account account = queryAccountDbPort.findByUserAndAccountType(user,accountType)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
        // 2. Transfer 정보를 가져온다.
        List<Transfer> transfers = queryTransferDbPort.findByAccount(account);
        Collections.sort(transfers);

        List<TransferResponse> transferResponse = new ArrayList<>();
        for(Transfer transfer: transfers){
            if(transfer.getReceiveAccount().equals(account)){
                transferResponse.add(TransferResponse.builder()
                        .type("입금")
                        .balance(transfer.getReceiveAccountBalance())
                        .amount(transfer.getAmount())
                        .transferDateTime(transfer.getTransferDateTime())
                        .build());
            }
            if(transfer.getSendAccount().equals(account)){
                transferResponse.add(TransferResponse.builder()
                        .type("출금")
                        .balance(transfer.getSendAccountBalance())
                        .amount(transfer.getAmount())
                        .transferDateTime(transfer.getTransferDateTime())
                        .build());
            }
        }
        return CheckDepositWithDrawServiceResponse.builder().transfer(transferResponse).build();
    }
}
