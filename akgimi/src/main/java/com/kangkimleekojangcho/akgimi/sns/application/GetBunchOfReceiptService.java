package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.sns.application.port.QueryFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.request.GetBunchOfReceiptServiceRequest;
import com.kangkimleekojangcho.akgimi.sns.application.response.GetBunchOfReceiptServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetBunchOfReceiptService {
    private final QueryFeedDbPort commandFeedDbPort;

    public GetBunchOfReceiptServiceResponse getBunchOfReceipt(
            Long userId, GetBunchOfReceiptServiceRequest serviceRequest
    ) {
        return GetBunchOfReceiptServiceResponse.builder()
                .bunchOfBriefReceiptInfo(commandFeedDbPort.findReceiptByUser_IdAndLastReceiptIdAndNumberOfReceipt(
                        userId, serviceRequest.lastReceiptId(), serviceRequest.numberOfReceipt()))
                .build();
    }
}
