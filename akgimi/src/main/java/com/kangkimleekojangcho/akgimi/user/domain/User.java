package com.kangkimleekojangcho.akgimi.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    public void setUserState(UserState userState) {
        this.userState = userState;
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

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public List<UserField> whatIsNotFilled() {
        List<UserField> userFields = new ArrayList<>();
        if(nickname==null){
            userFields.add(UserField.NICKNAME);
        }
        if(simplePassword==null){
            userFields.add(UserField.SIMPLE_PASSWORD);
        }
        return userFields;
    }
}
