package com.kangkimleekojangcho.akgimi.user.domain;

import lombok.Getter;

@Getter
public class KakaoPublicKey {
    private String kid;
    private String kty;
    private String alg;
    private String use;
    private String n;
    private String e;

}
