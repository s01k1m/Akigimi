package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.response.GetAllChallengesServiceResponse;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllChallengesService {
    private final QueryChallengeDbPort queryChallengeDbPort;
    //TODO : postscript 있는 지 여부

    public List<GetAllChallengesServiceResponse> getAllChallenges(Long userId){
        List<Challenge> challenges = queryChallengeDbPort.findAllByUserId(userId);
        return challenges.stream().map(GetAllChallengesServiceResponse::from).toList();
    }
}
