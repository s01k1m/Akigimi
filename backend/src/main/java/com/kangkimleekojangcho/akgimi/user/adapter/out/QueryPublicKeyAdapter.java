package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.application.port.QueryInternetPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryPublicKeyPort;
import com.kangkimleekojangcho.akgimi.user.config.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class QueryPublicKeyAdapter implements QueryPublicKeyPort {

    private final QueryInternetPort queryInternetPort;
    private final KakaoProperties kakaoProperties;

    public String fromKakao() {
        return queryInternetPort.get(kakaoProperties.kakaoPublicKeyUrl());
    }

    public String fromMyself() {
        return queryInternetPort.get(kakaoProperties.myselfPublicKeyUrl());
    }
}
