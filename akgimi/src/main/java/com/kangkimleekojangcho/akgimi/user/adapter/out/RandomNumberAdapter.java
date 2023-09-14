package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.application.port.RandomNumberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RandomNumberAdapter implements RandomNumberPort {

    @Override
    public int generate(int from, int to) {
        return 0;
    }
}
