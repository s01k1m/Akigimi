package com.kangkimleekojangcho.akgimi.sns.application.port;

import com.kangkimleekojangcho.akgimi.sns.domain.CountLike;

public interface CommandCountLikeDbPort {

    CountLike save(CountLike countLike);
}
