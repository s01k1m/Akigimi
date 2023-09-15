package com.kangkimleekojangcho.akgimi.bank.domain;

import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorException;
import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorExceptionCode;
import com.kangkimleekojangcho.akgimi.user.domain.RecommendNicknamePrefix;

public enum Bank {
    SSAFY, MULTI;
    public static Bank pick(int idx){
        if(idx> Bank.values().length){
            throw new ServerErrorException(ServerErrorExceptionCode.CANNOT_RECOMMEND_NICKNAME);
        }
        return Bank.values()[idx];
    }

    public static int howMany() {
        return Bank.values().length;
    }
}
