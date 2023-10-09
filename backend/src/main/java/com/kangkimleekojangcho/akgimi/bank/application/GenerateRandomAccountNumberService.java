package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.user.application.port.RandomNumberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateRandomAccountNumberService {
    private final RandomNumberPort randomNumberPort;

    public String generate() {
        return randomNumberPort.generateDigit(6)+"-"+randomNumberPort.generateDigit(7)+"-"+randomNumberPort.generateDigit(5);
    }
}
