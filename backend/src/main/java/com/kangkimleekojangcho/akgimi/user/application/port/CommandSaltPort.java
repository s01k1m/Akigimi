package com.kangkimleekojangcho.akgimi.user.application.port;

import com.kangkimleekojangcho.akgimi.user.domain.Salt;
import com.kangkimleekojangcho.akgimi.user.domain.SaltType;

public interface CommandSaltPort {
    void save(Salt salt);

    void deleteByUserIdAndType(Long userId, SaltType saltType);
}
