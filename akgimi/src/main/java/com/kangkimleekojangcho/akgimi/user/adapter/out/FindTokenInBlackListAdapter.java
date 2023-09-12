package com.kangkimleekojangcho.akgimi.user.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindTokenInBlackListAdapter {
    public boolean find(String tokenString) {
        return false;
    }
}
