package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.application.port.CommandBlackListPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryBlackListPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BlackListAdapter implements
        QueryBlackListPort, CommandBlackListPort {
    @Override
    public boolean find(String tokenString) {
        return false;
    }

    @Override
    public String add(String tokenString, long time) {
        return null;
    }
}
