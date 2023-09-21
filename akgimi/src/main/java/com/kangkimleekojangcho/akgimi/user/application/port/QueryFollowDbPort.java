package com.kangkimleekojangcho.akgimi.user.application.port;

import com.kangkimleekojangcho.akgimi.user.domain.User;

import java.util.List;

public interface QueryFollowDbPort {
    List<User> getFollowingUser(User user);

    List<User> getFollowedUser(User user);
}
