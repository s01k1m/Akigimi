package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.user.application.port.QueryIdTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetIdTokenService {
    private final QueryIdTokenPort queryIdTokenPort;

    public String getIdToken(String code){
        return queryIdTokenPort.get(code);
    }
}
