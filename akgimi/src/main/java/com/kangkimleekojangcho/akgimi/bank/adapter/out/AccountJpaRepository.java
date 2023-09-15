package com.kangkimleekojangcho.akgimi.bank.adapter.out;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<Account,Long> {
}
