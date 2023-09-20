package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.domain.Salt;
import com.kangkimleekojangcho.akgimi.user.domain.SaltType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaltJpaRepository extends JpaRepository<Salt,Long> {
    Optional<Salt> findByUserIdAndType(Long userId, SaltType saltType);

    void deleteByUserIdAndType(Long userId, SaltType saltType);
}
