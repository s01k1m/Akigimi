package com.kangkimleekojangcho.akgimi.user.config;



import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.time.Duration;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigProperties {
    private final String secretKey;
    private final Long accessTokenValidTimeInMinuteUnit;
    private final Long refreshTokenValidTimeInDayUnit;
    private final Long refreshTokenValidTimeInDayUnitInRedis; // 14일
    private final String tokenRedirectUrl;

    public byte[] getSecretKey() {
        return secretKey.getBytes();
    }

    public Long getAccessTokenValidTimeInMillisecondUnit() {
        return Duration.ofMinutes(accessTokenValidTimeInMinuteUnit).toMillis();
    }

    public Long getRefreshTokenValidTimeInMillisecondUnit() {
        return Duration.ofDays(refreshTokenValidTimeInDayUnit).toMillis();
    }

    public Long getRefreshTokenValidTimeInMillisecondUnitInRedis() {
        return Duration.ofDays(refreshTokenValidTimeInDayUnit).toMillis();
    }

    public String getTokenRedirectUrl() {
        return tokenRedirectUrl;
    }
}

