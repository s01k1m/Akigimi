package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.application.port.QueryInternetPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryPublicKeyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class QueryPublicKeyAdapter implements QueryPublicKeyPort {
    private final QueryInternetPort queryInternetPort;

    @Value("${kakao.publickey-url}")
    String kakaoPublicKeyUrl;
    @Value("${kakao.publickey-myself-url}")
    String myselfPublicKeyUrl;

    public String fromKakao() {
        return queryInternetPort.get(kakaoPublicKeyUrl);
    }

    public String fromMyself() {
        return queryInternetPort.get(myselfPublicKeyUrl);
    }

}
