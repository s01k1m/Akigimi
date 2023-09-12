package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter {
    private final UserJpaRepository userJpaRepository;

    public Optional<User> findByOauthId(String oauthId) {
        return userJpaRepository.findByOauthId(oauthId);
    }

    public User save(User user) {
        return userJpaRepository.save(user);
    }

    public Optional<User> findById(long userId) {
        return userJpaRepository.findById(userId);
    }
}
