package com.kangkimleekojangcho.akgimi.user.adapter.in;

import com.kangkimleekojangcho.akgimi.user.adapter.out.GetKakaoPublicKeyFromKakaoAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublicKeyController {
    private final GetKakaoPublicKeyFromKakaoAdapter getKakaoPublicKeyFromKakaoAdapter;
    @Cacheable(cacheNames = "KakaoOIDC", cacheManager = "oidcCacheManager")
    @GetMapping("/publickeys")
    public String getPublicKeys() {
        return getKakaoPublicKeyFromKakaoAdapter.get();
    }
}
