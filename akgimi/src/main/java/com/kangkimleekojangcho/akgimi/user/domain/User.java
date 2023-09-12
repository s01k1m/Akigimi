package com.kangkimleekojangcho.akgimi.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;
    @Enumerated(value = EnumType.STRING)
    private OauthProvider oauthProvider;

    @Enumerated(value = EnumType.STRING)
    private UserState userState;

    @Builder
    public User(Long id, String oauthId, OauthProvider oauthProvider, UserState userState) {
        this.id = id;
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.userState = userState;
    }

    public void signUp() {
        this.userState = UserState.ACTIVE;
    }

    public boolean isSignUpSccessfully() {
        return UserState.ACTIVE.equals(this.userState);
    }
}
