package com.kangkimleekojangcho.akgimi.user.domain;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class KakaoNickname {
    private final String value;

    public KakaoNickname(String value) {
        if(value==null){
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "카카오 닉네임이 입력되지 않았습니다.");
        }
        value = value.trim();

        String regexPattern = "[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s]";

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(value);

        value = matcher.replaceAll("");
        if(value.length()>10){
            value = value.substring(0, 10);
        }
        this.value = value;
    }
}
