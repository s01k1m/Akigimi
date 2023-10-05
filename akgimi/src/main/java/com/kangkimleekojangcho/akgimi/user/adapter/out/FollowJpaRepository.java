package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.domain.Follow;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

public interface FollowJpaRepository extends JpaRepository<Follow,Long> {
    @Query(value = "SELECT f.followee FROM Follow f WHERE f.follower = :user")
    List<User> findByFollower(User user);

    @Query(value = "SELECT f.follower FROM Follow f WHERE f.followee = :user")
    List<User> findByFollowee(User user);

    @Query(value = "SELECT f FROM Follow f WHERE f.follower = :follower AND f.followee = :followee")
    Optional<Follow> findByFollowerAndFollowee(User follower, User followee);
}
