package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryPostscriptDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.request.GetBunchOfPostscriptServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.response.GetBunchOfPostscriptServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetBunchOfPostscriptService {

    private final QueryPostscriptDbPort queryPostscriptDbPort;

    public GetBunchOfPostscriptServiceResponse execute(GetBunchOfPostscriptServiceRequest request) {
        if (request.numberOfPostscript() == 0) {
            return GetBunchOfPostscriptServiceResponse.builder()
                    .bunchOfPostscriptInfo(new ArrayList<>())
                    .build();
        }

        return queryPostscriptDbPort.findBunchOfPostscriptForProduct(request);
    }
}
