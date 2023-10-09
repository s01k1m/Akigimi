package com.kangkimleekojangcho.akgimi.ranking.application.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetAllRankingServiceResponse implements Comparable<GetAllRankingServiceResponse>{
    private final String userNickname;
    private final String userImgUrl;
    private final String productName;
    private final Integer percentage;

    @Override
    public int compareTo(GetAllRankingServiceResponse o) {
        return o.getPercentage() - getPercentage();
    }
}
