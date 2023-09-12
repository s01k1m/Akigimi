package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.user.adapter.out.GetIdTokenAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetIdTokenService {
    private final GetIdTokenAdapter getIdTokenAdapter;

    public String getIdToken(String code){
        return getIdTokenAdapter.get(code);
    }


}
