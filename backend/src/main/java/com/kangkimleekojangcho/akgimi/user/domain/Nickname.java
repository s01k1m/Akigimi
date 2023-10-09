package com.kangkimleekojangcho.akgimi.user.domain;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Getter
public class Nickname {
    private final String value;

    public Nickname(String value) {
        if (value == null) {
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "닉네임은 NULL 값일 수 없습니다.");
        }
        value = value.trim();
        if (value.length() < 10 || value.length() > 20) {
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT,"닉네임은 10자에서 20자 사이의 길이를 가져야 합니다.");
        }
        if (value.contains("  ")) {
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "닉네임에는 공백이 연속해서 두 개 이상 들어가선 안 됩니다.");
        }
        String regexPattern = ".*[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s].*";

        Pattern pattern = Pattern.compile(regexPattern);
        if(pattern.matcher(value).matches()){
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT,"닉네임에는 특수문자가 포함되면 안 됩니다.");
        }
        this.value = value;
    }
}
