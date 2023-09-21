package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.domain.Follow;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowJpaRepository extends JpaRepository<Follow,Long> {
    List<User> findByFollower(User user);

    List<User> findByFollowee(User user);
}
