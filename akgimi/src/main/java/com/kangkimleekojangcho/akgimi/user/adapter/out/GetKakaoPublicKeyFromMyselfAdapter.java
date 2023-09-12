package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.domain.KakaoPublicKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GetKakaoPublicKeyFromMyselfAdapter {
    private final GetDataFromInternetAdapter getDataFromInternetAdapter;

    @Value("${kakao.publickey-myself-url}")
    String myselfPublicKeyUrl;

    public String get() {
        return getDataFromInternetAdapter.get(myselfPublicKeyUrl);
    }
}
