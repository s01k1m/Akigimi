package com.kangkimleekojangcho.akgimi.user.domain;

import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorException;
import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorExceptionCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RecommendNicknamePrefix {
    노력하는,
    열심히_하는,
    잘생긴,
    ;
    @Override
    public String toString() {
        return this.name().replaceAll("_"," ");
    }

    public static String pick(int idx){
        if(idx>RecommendNicknamePrefix.values().length){
            throw new ServerErrorException(ServerErrorExceptionCode.CANNOT_RECOMMEND_NICKNAME);
        }
        return RecommendNicknamePrefix.values()[idx].toString();
    }

    public static int howMany() {
        return RecommendNicknamePrefix.values().length;
    }
}
