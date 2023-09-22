package com.kangkimleekojangcho.akgimi.user.application.port;

import com.kangkimleekojangcho.akgimi.user.domain.User;

import java.util.List;

public interface QueryFollowDbPort {
    List<User> getFollower(User user);

    List<User> getFollowee(User user);
}
