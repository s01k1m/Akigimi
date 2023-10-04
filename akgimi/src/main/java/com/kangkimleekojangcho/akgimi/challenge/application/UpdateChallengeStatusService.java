package com.kangkimleekojangcho.akgimi.challenge.application;
import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.response.UpdateChallengeStatusServiceResponse;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UpdateChallengeStatusService {
    private final QueryChallengeDbPort queryChallengeDbPort;
    private final CommandChallengeDbPort
    commandChallengeDbPort;

    public UpdateChallengeStatusServiceResponse update(Long userId, boolean succeed){
        Challenge challenge = queryChallengeDbPort.findInProgressChallengeByUserId(userId).orElseThrow(()-> new BadRequestException(BadRequestExceptionCode.NOT_PARTICIPATE_IN_CHALLENGE));
        if(succeed){
            challenge.update(true, LocalDate.now(), false);
            commandChallengeDbPort.save(challenge);
        }
        return  UpdateChallengeStatusServiceResponse.builder()
                .productUrl(challenge.getProduct().getUrl())
                .build();
    }
}
