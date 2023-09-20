package com.kangkimleekojangcho.akgimi.bank.adapter.in;

import com.kangkimleekojangcho.akgimi.bank.adapter.in.request.*;
import com.kangkimleekojangcho.akgimi.bank.application.*;
import com.kangkimleekojangcho.akgimi.bank.application.request.CreateAccountPasswordServiceRequest;
import com.kangkimleekojangcho.akgimi.bank.application.request.CreateAccountServiceRequest;
import com.kangkimleekojangcho.akgimi.bank.application.response.CheckBalanceServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.application.response.CheckDepositWithDrawServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.application.response.CreateAccountPasswordServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.application.response.CreateAccountServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BankController {

    private final CreateAccountPasswordService createAccountPasswordService;
    private final MakeTransferService makeTransferService;
    private final CheckBalanceService checkBalanceService;
    private final CheckDepositWithdrawService checkDepositWithdrawService;
    private final SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;
    private final GenereateAccountService generateAccountService;
    // 새 계좌 생성
    @PostMapping("/account/new")
    public ResponseEntity<SuccessResponse<CreateAccountServiceResponse>> createAccount(
            @RequestBody CreateAccountRequest request,
            HttpServletRequest servletRequest) {
        long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        if (request.getAccountType() == null || request.getBank() == null)
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT);
        Bank bank = request.getBank();
        AccountType accountType = request.getAccountType();
        CreateAccountServiceRequest serviceRequest = new CreateAccountServiceRequest(bank,accountType);
        CreateAccountServiceResponse createAccountServiceResponse = generateAccountService.create(userId,serviceRequest);
        return ResponseFactory.success(createAccountServiceResponse);
    }


    // 새 계좌 비밀번호 확인
    @PostMapping("/account/new/password")
    public ResponseEntity<SuccessResponse<CreateAccountPasswordServiceResponse>> createAccountPassword(
            @RequestBody CreateAccountPasswordRequest request,
            HttpServletRequest servletRequest) {
        long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        if (request.getPassword() == null || request.getBank() == null || request.getAccountNumber() == null)
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT);
        CreateAccountPasswordServiceRequest createAccountPasswordServiceRequest = request.toServiceRequest();
        CreateAccountPasswordServiceResponse createAccountPasswordServiceResponse = createAccountPasswordService.createAccountPassword(userId, createAccountPasswordServiceRequest);
        return ResponseFactory.success(createAccountPasswordServiceResponse);

    }

    // 계좌 이체 (절약기록)
    @PostMapping("/account/deposit")
    public ResponseEntity<SuccessResponse<Boolean>> makeDeposit(
            @RequestBody MakeDepositRequest request, HttpServletRequest servletRequest) {
        long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        if (request.getAmount() <= 0)
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT);
        long amount = request.getAmount();
        makeTransferService.makeDeposit(userId, amount);
        return ResponseFactory.success(true);
    }

    // 계좌 이체 (챌린지)
    @PostMapping("/account/withdraw")
    public ResponseEntity<SuccessResponse<Boolean>> makeWithdraw(
            @RequestBody MakeWithdrawRequest request, HttpServletRequest servletRequest) {
        long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        if (request.getUserPassword() == null)
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT);
        String userPassword = request.getUserPassword();
        makeTransferService.makeWithdraw(userId, userPassword);
        return ResponseFactory.success(true);
    }

    // 계좌 잔액 조회
    @GetMapping("/account/amount")
    public ResponseEntity<SuccessResponse<CheckBalanceServiceResponse>> checkBalance(@RequestParam("accountType") AccountType accountType, HttpServletRequest servletRequest) {
        long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        if (accountType == null) throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT);
        CheckBalanceServiceResponse response = checkBalanceService.checkBalance(userId, accountType);
        return ResponseFactory.success(response);
    }

    // 계좌 입출금 내역 조회
    @GetMapping("/account/transaction/history")
    public ResponseEntity<SuccessResponse<CheckDepositWithDrawServiceResponse>> checkDepositWithdraw(@RequestParam("accountType") AccountType accountType, HttpServletRequest servletRequest) {
        long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        if (accountType == null) throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT);
        CheckDepositWithDrawServiceResponse response = checkDepositWithdrawService.checkDepositWithdraw(userId, accountType);
        return ResponseFactory.success(response);
    }
}
