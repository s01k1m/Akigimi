package com.example.demo.bank.application.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SaveMoneyServiceRequest {
    private final String from;
    private final String to;
    private final Long moneyValue;
}
