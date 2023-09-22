package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.application.port.QueryFollowDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QueryFollowJpaAdapter implements QueryFollowDbPort {
    private final FollowJpaRepository followJpaRepository;
    @Override
    public List<User> getFollower(User user) {
        return followJpaRepository.findByFollower(user);
    }

    @Override
    public List<User> getFollowee(User user) {
        return followJpaRepository.findByFollowee(user);
    }
}
