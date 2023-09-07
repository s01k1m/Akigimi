package com.example.demo.bank.adapter.in;

import com.example.demo.bank.adapter.in.request.SaveMoneyRequest;
import com.example.demo.bank.application.DepositService;
import com.example.demo.bank.application.SaveMoneyService;
import com.example.demo.bank.application.response.SaveMoneyServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BankController {
    private final SaveMoneyService saveMoneyService;
    private final DepositService depositService;

    @PostMapping("/bank/money")
    public ResponseEntity<SaveMoneyServiceResponse> save(@RequestBody SaveMoneyRequest request) {
        return new ResponseEntity<>(saveMoneyService.save(request.toServiceRequest()), HttpStatus.OK);
    }
}
