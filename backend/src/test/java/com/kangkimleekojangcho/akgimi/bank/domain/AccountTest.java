package com.kangkimleekojangcho.akgimi.bank.domain;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    @DisplayName("[happy] 적절한 값이 들어왔을 때 입금을 한다")
    @CsvSource(value = {"30000:10000", "40000:70000"}, delimiter = ':')
    @ParameterizedTest
    public void deposit(Long balanceBeforeDeposit, Long depositAmount) throws Exception {
        //given
        Account depositAccount = Account.builder()
                .accountNumber("432-1123-4151-1665")
                .accountType(AccountType.DEPOSIT)
                .balance(balanceBeforeDeposit)
                .bank(Bank.SSAFY)
                .isDeleted(false)
                .isPasswordRegistered(true)
                .password("1234")
                .id(1L)
                .build();

        //when
        depositAccount.deposit(depositAmount);

        //then
        Assertions.assertThat(depositAccount.getBalance()).isEqualTo(balanceBeforeDeposit + depositAmount);
    }

    @DisplayName("[bad] 최대 입금액을 초과하는 경우 오류를 반환한다.")
    @Test
    public void throwErrorWhenDepositExceedMaximumValue() throws Exception {
        //given
        Account depositAccount = Account.builder()
                .accountNumber("432-1123-4151-1665")
                .accountType(AccountType.DEPOSIT)
                .balance(10000L)
                .bank(Bank.SSAFY)
                .isDeleted(false)
                .isPasswordRegistered(true)
                .password("1234")
                .id(1L)
                .build();

        //when //then
        assertThatThrownBy(() -> depositAccount.deposit(Long.MAX_VALUE))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(BadRequestExceptionCode.EXCEED_MAXIMUM_BALANCE.getDescriptionMessage());
    }

    @DisplayName("[happy] 출금액이 현재 출금계좌의 보유액보다 작다면 출금한다")
    @CsvSource(value = {"30000:10000", "40000:10000"}, delimiter = ':')
    @ParameterizedTest
    public void withdrawal(Long balanceBeforeWithdrawal, Long withdrawalAmount) throws Exception {
        //given
        Account depositAccount = Account.builder()
                .accountNumber("432-1123-4151-1665")
                .accountType(AccountType.DEPOSIT)
                .balance(balanceBeforeWithdrawal)
                .bank(Bank.SSAFY)
                .isDeleted(false)
                .isPasswordRegistered(true)
                .password("1234")
                .id(1L)
                .build();

        //when
        depositAccount.withdraw(withdrawalAmount);

        //then
        Assertions.assertThat(depositAccount.getBalance()).isEqualTo(balanceBeforeWithdrawal - withdrawalAmount);
    }

    @DisplayName("[bad] 출금액이 출금계좌 보유액을 초과하는 경우 오류를 반환한다.")
    @CsvSource(value = {"30000:40000", "40000:70000"}, delimiter = ':')
    @ParameterizedTest
    public void throwErrorWhenAccountBalanceLack(Long balanceBeforeWithdrawal, Long withdrawalAmount) throws Exception {
        //given
        Account depositAccount = Account.builder()
                .accountNumber("432-1123-4151-1665")
                .accountType(AccountType.DEPOSIT)
                .balance(balanceBeforeWithdrawal)
                .bank(Bank.SSAFY)
                .isDeleted(false)
                .isPasswordRegistered(true)
                .password("1234")
                .id(1L)
                .build();

       assertThatThrownBy(() -> depositAccount.withdraw(withdrawalAmount))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(BadRequestExceptionCode.LACK_OF_ACCOUNT_BALANCE.getDescriptionMessage());
    }

}