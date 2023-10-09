package com.kangkimleekojangcho.akgimi.user.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao")
public record KakaoProperties(

        String kakaoRedirectUrl,

        String kakaoRestApiKey,

        String kakaoTokenRedirectUrl,

        String kakaoPublicKeyUrl,

        String myselfPublicKeyUrl,

        String kakaoIdTokenUrl,

        String iss,

        String restApiKey
) {

}
