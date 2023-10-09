package com.kangkimleekojangcho.akgimi.user.application.port;

import com.kangkimleekojangcho.akgimi.user.domain.User;

public interface CommandUserDbPort {
    User save(User user);
}
