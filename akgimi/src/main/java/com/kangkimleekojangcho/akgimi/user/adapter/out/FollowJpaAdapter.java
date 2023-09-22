package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.application.port.CommandFollowDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowJpaAdapter implements CommandFollowDbPort {
    private final FollowJpaRepository followJpaRepository;
    @Override
    public void save(Follow follow) {
        followJpaRepository.save(follow);

    }
}
