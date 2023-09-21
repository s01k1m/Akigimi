package com.kangkimleekojangcho.akgimi.bank.adapter.out;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.Transfer;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransferJpaRepository extends JpaRepository<Transfer, Long> {

    @Query(value = "SELECT t FROM Transfer t WHERE t.sendAccount = :account OR t.receiveAccount = :account")
    List<Transfer> findByAccount(@Param("account") Account account);
}
