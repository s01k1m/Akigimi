package com.kangkimleekojangcho.akgimi.user.application.port;

import com.kangkimleekojangcho.akgimi.user.domain.Salt;

public interface CommandSaltPort {
    void save(Salt salt);
}
