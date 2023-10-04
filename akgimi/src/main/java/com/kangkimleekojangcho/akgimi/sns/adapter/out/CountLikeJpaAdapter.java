package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.application.port.CommandCountLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.domain.CountLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountLikeJpaAdapter implements CommandCountLikeDbPort {

    private final CountLikeJpaRepository countLikeJpaRepository;

    @Override
    public CountLike save(CountLike countLike) {
        return countLikeJpaRepository.save(countLike);
    }
}
