package com.kangkimleekojangcho.akgimi.user.domain;

import lombok.Getter;

@Getter
public class KakaoIdToken {
    /** issuer ex https://kauth.kakao.com */
    private final String iss;
    /** client id */
    private final String aud;
    /** oauth provider account unique id */
    private final String sub;

    private final String email;

    private final String nickname;

    private final String profile_img_url;

    public KakaoIdToken(String iss, String aud, String sub, String email, String nickname, String profile_img_url) {
        this.iss = iss;
        this.aud = aud;
        this.sub = sub;
        this.email = email;
        this.nickname = nickname;
        this.profile_img_url = profile_img_url;
    }
}
