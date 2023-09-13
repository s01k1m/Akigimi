package com.kangkimleekojangcho.akgimi.user.adapter.in;

import com.kangkimleekojangcho.akgimi.user.application.port.QueryPublicKeyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublicKeyController {
    private final QueryPublicKeyPort queryPublicKeyPort;
    @Cacheable(cacheNames = "KakaoOIDC", cacheManager = "oidcCacheManager")
    @GetMapping("/publickeys")
    public String getPublicKeys() {
        return queryPublicKeyPort.fromKakao();
    }
}
