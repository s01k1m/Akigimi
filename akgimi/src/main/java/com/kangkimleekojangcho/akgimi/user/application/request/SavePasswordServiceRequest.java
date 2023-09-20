package com.kangkimleekojangcho.akgimi.user.application.request;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class SavePasswordServiceRequest {
    private final Long accountId;
    private final String password;

    public SavePasswordServiceRequest(Long accountId, String password) {
        this.accountId = accountId;
        if(password == null){
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "비밀번호를 입력해야 합니다.");
        }
        if(password.length()!=6){
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "비밀번호의 자릿수는 6이어야 합니다.");
        }
        String pattern = "^\\d+$";
        if(!Pattern.matches(pattern,password)){
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "비밀번호는 숫자로 구성되어 있어야 합니다.");
        }
        this.password = password;
    }
}
