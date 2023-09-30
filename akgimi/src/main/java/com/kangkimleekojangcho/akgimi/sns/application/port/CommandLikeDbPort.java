package com.kangkimleekojangcho.akgimi.sns.application.port;

import com.kangkimleekojangcho.akgimi.sns.domain.Like;

public interface CommandLikeDbPort {
    Like save(Like like);
}
