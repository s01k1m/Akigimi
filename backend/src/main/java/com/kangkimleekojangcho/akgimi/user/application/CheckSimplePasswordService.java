package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.HashPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QuerySaltPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.AuthorizeBySimplePasswordResponse;
import com.kangkimleekojangcho.akgimi.user.domain.Salt;
import com.kangkimleekojangcho.akgimi.user.domain.SaltType;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckSimplePasswordService {
    private final HashPort hashPort;
    private final QuerySaltPort querySaltPort;
    private final QueryUserDbPort queryUserDbPort;
    private final JwtTokenIssuer jwtTokenIssuer;

    /**
     * 간편 비밀번호를 체크하고, 새로운 Access Token을 발급한다.
     * - 간편 비밀번호가 설정되어 있지 않으면, 404 메시지를 보낸다.
     * - 간편 비밀번호가 일치하지 않으면, `isPasswordCorrect` 값이 false인 메시지를 보낸다.
     * - 간편 비밀번호가 일치하면, `isPasswordCorrect` 값이 true인 메시지에 새로운 access token을 담아 보낸다.
     * @param userId 유저 아이디
     * @param simplePassword 간편 비밀번호
     * @return
     */
    @Transactional(readOnly = true)
    public AuthorizeBySimplePasswordResponse authorize(Long userId, String simplePassword) {
        Salt salt = getSalt(userId);
        User user = getUser(userId);
        checkPasswordIsSetted(user);
        String hashed = hashPort.hash(simplePassword, salt.getSaltValue());
        if(!hashed.equals(user.getSimplePassword())){
            return new AuthorizeBySimplePasswordResponse(false, null, null, null);
        }
        String accessToken = jwtTokenIssuer.createAccessTokenForAuthorizedUser(user);
        return new AuthorizeBySimplePasswordResponse(true, accessToken, user.getUserState().name(), user.getId());
    }

    private Salt getSalt(Long userId) {
        Salt salt = querySaltPort.findByUserIdAndSaltType(userId, SaltType.SIMPLE).orElseThrow(() -> new UnauthorizedException(UnauthorizedExceptionCode.INVALID_SIMPLE_PASSWORD));
        return salt;
    }

    private User getUser(Long userId) {
        User user = queryUserDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        return user;
    }

    private void checkPasswordIsSetted(User user) {
        if (!user.isPasswordSetted()) {
            throw new BadRequestException(BadRequestExceptionCode.PASSWORD_NOT_SETTED);
        }
    }
}
