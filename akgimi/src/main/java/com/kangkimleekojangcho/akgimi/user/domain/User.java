package com.kangkimleekojangcho.akgimi.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;

    @Column(unique = true)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private OidcProvider oidcProvider;

    @Enumerated(value = EnumType.STRING)
    private UserState userState;
    private String kakaoProfileNickname;
    private String simplePassword;
    private String profileImageUrl;

    @Builder
    public User(Long id, String oauthId, String nickname, OidcProvider oidcProvider, UserState userState, KakaoNickname kakaoProfileNickname, String simplePassword, String profileImageUrl) {
        this.id = id;
        this.oauthId = oauthId;
        this.nickname = nickname;
        this.oidcProvider = oidcProvider;
        this.userState = userState;
        this.kakaoProfileNickname = kakaoProfileNickname.getValue();
        this.simplePassword = simplePassword;
        this.profileImageUrl = profileImageUrl;
    }



    public void signUp() {
        this.userState = UserState.ACTIVE;
    }

    public boolean isSignUpSccessfully() {
        return UserState.ACTIVE.equals(this.userState);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSimplePassword(String simplePassword) {
        this.simplePassword = simplePassword;
    }
}
