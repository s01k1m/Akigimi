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

    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private OidcProvider oidcProvider;

    @Enumerated(value = EnumType.STRING)
    private UserState userState;

    @Builder
    public User(Long id, String oauthId, OidcProvider oidcProvider, UserState userState) {
        this.id = id;
        this.oauthId = oauthId;
        this.oidcProvider = oidcProvider;
        this.userState = userState;
    }

    public void signUp() {
        this.userState = UserState.ACTIVE;
    }

    public boolean isSignUpSccessfully() {
        return UserState.ACTIVE.equals(this.userState);
    }
}
