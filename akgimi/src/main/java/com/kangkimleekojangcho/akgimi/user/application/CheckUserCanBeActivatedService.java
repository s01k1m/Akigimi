package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.CheckUserCanBeActivatedServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.ActivateUserFailureCause;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserField;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckUserCanBeActivatedService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountDbPort queryAccountDbPort;
    public CheckUserCanBeActivatedServiceResponse check(Long userId) {
        User user = queryUserDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        if(!UserState.PENDING.equals(user.getUserState())){
            return alreadyActivated();
        }
        List<UserField> unfilledFields = user.whatIsNotFilled();
        checkWithdrawlAccount(user, unfilledFields);
        checkDepositAccount(user, unfilledFields);
        if(!unfilledFields.isEmpty()){
            return fieldNotFilled(unfilledFields);
        }
        return success();
    }

    private void checkDepositAccount(User user, List<UserField> unfilledFields) {
        Optional<Account> depositOpt = queryAccountDbPort.findByUserAndAccountType(user, AccountType.DEPOSIT);
        if (depositOpt.isEmpty()) {
            unfilledFields.add(UserField.DEPOSIT);
            unfilledFields.add(UserField.DEPOSIT_PASSWORD);
        } else if (!depositOpt.get().getIsPasswordRegistered()){
            unfilledFields.add(UserField.DEPOSIT_PASSWORD);
        }
    }

    private void checkWithdrawlAccount(User user, List<UserField> unfilledFields) {
        Optional<Account> withdrawOpt = queryAccountDbPort.findByUserAndAccountType(user, AccountType.WITHDRAW);
        if(withdrawOpt.isEmpty()){
            unfilledFields.add(UserField.WITHDRAW);
            unfilledFields.add(UserField.WITHDRAW_PASSWORD);
        } else if(!withdrawOpt.get().getIsPasswordRegistered()){
            unfilledFields.add(UserField.WITHDRAW_PASSWORD);
        }
    }

    private static CheckUserCanBeActivatedServiceResponse success() {
        return new CheckUserCanBeActivatedServiceResponse(
                true, null, null
        );
    }

    private static CheckUserCanBeActivatedServiceResponse fieldNotFilled(List<UserField> unfilledFields) {
        return new CheckUserCanBeActivatedServiceResponse(
                false, ActivateUserFailureCause.NOT_FILLED,
                unfilledFields
        );
    }

    private static CheckUserCanBeActivatedServiceResponse alreadyActivated() {
        return new CheckUserCanBeActivatedServiceResponse(
                false, ActivateUserFailureCause.ALREADY_ACTIVE,
                new ArrayList<>()
        );
    }


}
