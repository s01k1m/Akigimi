package com.kangkimleekojangcho.akgimi.bank.adapter.out;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountTypeAndUserId(AccountType deposit, Long userId);

    Optional<Account> findByAccountNumberAndAccountType(String accountNumber, AccountType accountType);

    Optional<Account> findByUserAndAccountType(User user, AccountType accountType);

    Optional<Account> findByUserIdAndAccountTypeAndAccountNumber(Long userId, AccountType accountType, String accountNumber);
}
