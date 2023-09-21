package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.google.gson.Gson;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryIdTokenPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryInternetPort;
import com.kangkimleekojangcho.akgimi.user.config.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
class QueryIdTokenAdapter implements QueryIdTokenPort {
    private final KakaoProperties kakaoProperties;
    private final QueryInternetPort queryInternetPort;

    public String get(String code) {
        HashMap<String, String> info = new HashMap<>();
        info.put("grant_type", "authorization_code");
        info.put("client_id", kakaoProperties.kakaoRestApiKey());
        info.put("redirect_uri", kakaoProperties.kakaoRedirectUrl());
        info.put("code", code);
        String result = queryInternetPort.post(kakaoProperties.kakaoIdTokenUrl(), info);
        Gson gson = new Gson();
        return (String) gson.fromJson(result, Map.class).get("id_token");
    }
}
