package com.kangkimleekojangcho.akgimi.user.application.port;

import com.kangkimleekojangcho.akgimi.user.domain.Salt;

import java.util.Optional;

public interface QuerySaltPort {
    Optional<Salt> findById(Long id);
}
