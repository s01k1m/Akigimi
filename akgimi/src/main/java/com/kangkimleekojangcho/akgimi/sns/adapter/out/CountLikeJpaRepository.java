package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.domain.CountLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountLikeJpaRepository extends JpaRepository<CountLike, Long> {
}
