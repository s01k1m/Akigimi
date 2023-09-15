package com.kangkimleekojangcho.akgimi.sns.application.port;


import com.kangkimleekojangcho.akgimi.sns.domain.Feed;

public interface CommandFeedDbPort {
    Feed save(Feed feed);
}
