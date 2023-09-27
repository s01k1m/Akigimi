package com.kangkimleekojangcho.akgimi.ranking.application.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetAllRankingServiceResponse {
    private String userNickname;
    private String userImgUrl;
    private String productName;
    private Integer percentage;
}
