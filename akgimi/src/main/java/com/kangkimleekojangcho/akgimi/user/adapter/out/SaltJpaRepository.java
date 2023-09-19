package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.domain.Salt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaltJpaRepository extends JpaRepository<Salt,Long> {
}
