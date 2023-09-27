package com.kangkimleekojangcho.akgimi.user.domain;

import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class AccessToken extends JwtToken {


    public AccessToken(String rawToken, Jws<Claims> claimsJws) {
        super(rawToken, claimsJws);
    }

    public boolean isSimplePasswordChecked(){
        return Boolean.parseBoolean(getValue("isSimplePasswordChecked").orElseThrow(() -> new UnauthorizedException(UnauthorizedExceptionCode.INVALID_TOKEN)));
    }
}
