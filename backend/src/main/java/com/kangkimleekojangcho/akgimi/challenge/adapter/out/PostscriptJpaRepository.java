package com.kangkimleekojangcho.akgimi.challenge.adapter.out;

import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.challenge.domain.Postscript;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostscriptJpaRepository extends JpaRepository<Postscript, Long> {
    Optional<Postscript> findPostscriptByChallenge(Challenge challenge);
}
