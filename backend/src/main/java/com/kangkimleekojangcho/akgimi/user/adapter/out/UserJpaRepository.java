package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface UserJpaRepository extends JpaRepository<User,Long> {
    Optional<User> findByOauthId(String oauthId);

    Boolean existsByNickname(String nickname);

    @Query("select u from User u where u.nickname like %:nickname%")
    List<User> findByNickname(String nickname);
}
