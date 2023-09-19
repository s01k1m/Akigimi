package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.sns.application.port.QueryFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.request.GetBunchOfFeedWrittenByFollowerRequestServiceRequest;
import com.kangkimleekojangcho.akgimi.sns.application.response.GetBunchOfFeedWrittenByFollowerServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetBunchOfFeedWrittenByFollowerRequestService {

    private final QueryFeedDbPort commandFeedDbPort;

    public GetBunchOfFeedWrittenByFollowerServiceResponse getBunchOfFeed(
                    Long userId, GetBunchOfFeedWrittenByFollowerRequestServiceRequest serviceRequest
    ) {
        return GetBunchOfFeedWrittenByFollowerServiceResponse.builder()
                .bunchOfBriefFeedInfo(commandFeedDbPort.findByUser_IdAndLastFeedIdAndNumberOfFeed(
                        userId, serviceRequest.lastFeedId(), serviceRequest.numberOfFeed()))
                .build();
    }
}
