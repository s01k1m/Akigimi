package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GetIdTokenAdapter {
    @Value("${kakao.idtoken-url}")
    String kakaoIdTokenUrl;
    @Value("${kakao.rest-api-key}")
    String kakaoRestApiKey;
    @Value("${kakao.redirection-url}")
    String kakaoRedirectionUrl;
    private final GetDataFromInternetAdapter getDataFromInternetAdapter;

    public String get(String code){
        HashMap<String, String> info = new HashMap<>();
        info.put("grant_type","authorization_code");
        info.put("client_id",kakaoRestApiKey);
        info.put("redirect_uri", kakaoRedirectionUrl);
        info.put("code", code);
        String result = getDataFromInternetAdapter.post(kakaoIdTokenUrl, info);
        Gson gson = new Gson();
        return (String)gson.fromJson(result,Map.class).get("id_token");
    }
}
