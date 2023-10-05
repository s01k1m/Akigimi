package com.kangkimleekojangcho.akgimi.user.application.port;

import com.kangkimleekojangcho.akgimi.user.domain.Follow;
import com.kangkimleekojangcho.akgimi.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface QueryFollowDbPort {
    List<User> getFollower(User user);

    List<User> getFollowee(User user);

    Optional<Follow> isFollowed(User followee, User follower);
}
