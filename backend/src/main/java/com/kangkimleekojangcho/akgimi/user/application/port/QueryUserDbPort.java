package com.kangkimleekojangcho.akgimi.user.application.port;

import com.kangkimleekojangcho.akgimi.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface QueryUserDbPort {
    Optional<User> findById(long userId);

    Optional<User> findByOauthId(String oauthId);

    boolean existsByNickname(String nickname);

    User findReferenceById(Long userId);

    List<User> findByNickname(String nickname);
}
