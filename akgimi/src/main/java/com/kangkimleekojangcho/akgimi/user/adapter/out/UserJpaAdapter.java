package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class UserJpaAdapter implements QueryUserDbPort, CommandUserDbPort {
    private final UserJpaRepository userJpaRepository;

    public Optional<User> findByOauthId(String oauthId) {
        return userJpaRepository.findByOauthId(oauthId);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

    @Override
    public User findReferenceById(Long userId) {
        try {
            return userJpaRepository.getReferenceById(userId);
        } catch (EntityNotFoundException ex) {
            throw new BadRequestException(BadRequestExceptionCode.NOT_USER);
        }
    }

    public User save(User user) {
        return userJpaRepository.save(user);
    }

    public Optional<User> findById(long userId) {
        return userJpaRepository.findById(userId);
    }
}
