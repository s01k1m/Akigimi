package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.user.domain.KakaoPublicKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoPublicKeyGenerator {
    public List<KakaoPublicKey> generate(String raw) {
        String array = OptimizedJsonParser.getObject(raw, "keys");
        return OptimizedJsonParser.parse(array, KakaoPublicKey.class);
    }
}
