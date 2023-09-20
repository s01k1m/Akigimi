package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.common.application.port.BeanBusinessValidationService;
import com.kangkimleekojangcho.akgimi.common.domain.ScrollConstant;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.request.GetBunchOfFeedWrittenByFollowerRequestServiceRequest;
import com.kangkimleekojangcho.akgimi.sns.application.response.GetBunchOfFeedWrittenByFollowerServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetBunchOfFeedWrittenByFollowerRequestService {

    private final QueryFeedDbPort commandFeedDbPort;
    private final BeanBusinessValidationService<GetBunchOfFeedWrittenByFollowerRequestServiceRequest> validationService;

    public GetBunchOfFeedWrittenByFollowerServiceResponse getBunchOfFeed(
            Long userId, GetBunchOfFeedWrittenByFollowerRequestServiceRequest serviceRequest
    ) {
        validationService.validate(serviceRequest);
        if (serviceRequest.numberOfFeed() == 0) {
            return GetBunchOfFeedWrittenByFollowerServiceResponse.builder()
                    .bunchOfBriefFeedInfo(new ArrayList<>())
                    .build();
        }
        return GetBunchOfFeedWrittenByFollowerServiceResponse.builder()
                .bunchOfBriefFeedInfo(commandFeedDbPort.findByUser_IdAndLastFeedIdAndNumberOfFeed(
                        userId, serviceRequest.lastFeedId(), serviceRequest.numberOfFeed()))
                .build();
    }
}
