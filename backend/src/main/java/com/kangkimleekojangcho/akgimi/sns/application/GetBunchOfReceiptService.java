package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.common.application.port.BeanBusinessValidationService;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.request.GetBunchOfReceiptServiceRequest;
import com.kangkimleekojangcho.akgimi.sns.application.response.GetBunchOfReceiptServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetBunchOfReceiptService {

    private final QueryFeedDbPort commandFeedDbPort;
    private final BeanBusinessValidationService<GetBunchOfReceiptServiceRequest> validationService;

    public GetBunchOfReceiptServiceResponse getBunchOfReceipt(
            Long requestUserId, Long receiptOwnerId, GetBunchOfReceiptServiceRequest serviceRequest
    ) {
        validationService.validate(serviceRequest);

        if(serviceRequest.numberOfReceipt()==0) {
            return GetBunchOfReceiptServiceResponse.builder()
                    .bunchOfBriefReceiptInfo(new ArrayList<>())
                    .build();
        }
        return GetBunchOfReceiptServiceResponse.builder()
                .bunchOfBriefReceiptInfo(commandFeedDbPort.findReceiptByUser_IdAndLastReceiptIdAndNumberOfReceipt(
                        requestUserId,receiptOwnerId,serviceRequest.lastReceiptId(), serviceRequest.numberOfReceipt()))
                .build();
    }
}
