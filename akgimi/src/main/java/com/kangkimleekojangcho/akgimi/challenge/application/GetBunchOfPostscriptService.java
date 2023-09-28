package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryPostscriptDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.request.GetBunchOfPostscriptServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetBunchOfPostscriptService {

    private final QueryPostscriptDbPort queryPostscriptDbPort;

    public Void execute(GetBunchOfPostscriptServiceRequest request, Long userId) {
       return (Void) new Object();
    }
}
