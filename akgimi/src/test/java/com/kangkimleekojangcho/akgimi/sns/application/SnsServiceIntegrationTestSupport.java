package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandChallengeDbPort;
import com.kangkimleekojangcho.akgimi.config.ServiceIntegrationTestSupport;
import com.kangkimleekojangcho.akgimi.product.application.port.CommandProductDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryFeedDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandFollowDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserDbPort;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SnsServiceIntegrationTestSupport extends ServiceIntegrationTestSupport {

    @Autowired
    protected CommandProductDbPort commandProductDbPort;

    @Autowired
    protected CommandUserDbPort commandUserDbPort;

    @Autowired
    protected CommandChallengeDbPort commandChallengeDbPort;

    @Autowired
    protected CommandAccountDbPort commandAccountDbPort;

    @Autowired
    protected QueryFeedDbPort queryFeedDbPort;

    @Autowired
    protected CommandFeedDbPort commandFeedDbPort;

    @Autowired
    protected CommandFollowDbPort commandFollowDbPort;
}
