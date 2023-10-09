package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.application.port.CommandSaltPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QuerySaltPort;
import com.kangkimleekojangcho.akgimi.user.domain.Salt;
import com.kangkimleekojangcho.akgimi.user.domain.SaltType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SaltJpaAdapter implements QuerySaltPort, CommandSaltPort {
    private final SaltJpaRepository saltJpaRepository;


    @Override
    public void save(Salt salt) {
        saltJpaRepository.save(salt);
    }

    @Override
    public void deleteByUserIdAndType(Long userId, SaltType saltType) {
        saltJpaRepository.deleteByUserIdAndType(userId, saltType);
    }

    @Override
    public Optional<Salt> findById(Long id) {
        return saltJpaRepository.findById(id);
    }

    @Override
    public Optional<Salt> findByUserIdAndSaltType(Long userId, SaltType saltType) {
        return saltJpaRepository.findByUserIdAndType(userId, saltType);
    }
}
