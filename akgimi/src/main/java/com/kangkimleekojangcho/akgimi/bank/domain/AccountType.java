package com.kangkimleekojangcho.akgimi.bank.domain;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;

public enum AccountType {
    DEPOSIT, WITHDRAW;

    public static AccountType stringToAccountType(String type){
        if(type.equals("DEPOSIT")){
            return DEPOSIT;
        }
        if(type.equals("WITHDRAW")){
            return WITHDRAW;
        }
        throw new BadRequestException(BadRequestExceptionCode.NO_AVAILABLE_ACCOUNT_TYPE);
    }

}
