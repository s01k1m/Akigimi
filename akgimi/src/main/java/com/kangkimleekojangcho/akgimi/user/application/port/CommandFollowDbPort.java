package com.kangkimleekojangcho.akgimi.user.application.port;

import com.kangkimleekojangcho.akgimi.user.domain.Follow;

public interface CommandFollowDbPort {
    void save(Follow follow);
}
