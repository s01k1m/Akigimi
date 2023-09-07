package com.example.demo.bank.adapter.in.request;

import com.example.demo.bank.application.request.SaveMoneyServiceRequest;
import lombok.Getter;

@Getter
public class SaveMoneyRequest {
    // validation 어노테이션 붙이기
    private String from;
    private String to;
    private Long moneyValue;

    public SaveMoneyServiceRequest toServiceRequest() {
        return new SaveMoneyServiceRequest(from, to, moneyValue);
    }
}
