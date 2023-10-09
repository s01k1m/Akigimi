package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.response.GetParticipantNumberServiceResponse;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.product.application.port.QueryProductDbPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetParticipantNumberService {
    private final QueryChallengeDbPort queryChallengeDbPort;
    private final QueryProductDbPort queryProductDbPort;

    public GetParticipantNumberServiceResponse get(Long productId){
        queryProductDbPort.findById(productId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_PRODUCT));
        Integer count = queryChallengeDbPort.countByProductIdAndIsInProgress(productId, true);
        return GetParticipantNumberServiceResponse.from(count);
    }
}
