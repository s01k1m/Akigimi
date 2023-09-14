package com.kangkimleekojangcho.akgimi.bank.adapter.in.request;

import lombok.Getter;

@Getter
public class MakeTransferRequest {
    // TODO 절약 기록 타입, 챌린지 타입 수정 필요
    private String type;
    private Long amount;
    private String userPassowrd;
}
