package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.challenge.application.request.CreatePostscriptServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandPostscriptDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryPostscriptDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.response.CreatePostscriptServiceResponse;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.challenge.domain.Postscript;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandImagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CreatePostscriptService {

    private final CommandImagePort commandImagePort;
    private final QueryPostscriptDbPort postscriptDbPort;
    private final CommandPostscriptDbPort commandPostscriptDbPort;
    private final QueryChallengeDbPort challengeQueryDbPort;

    public CreatePostscriptServiceResponse createPostscript(
            CreatePostscriptServiceRequest createPostscriptServiceRequest,
            Long userId
    ) {
        Challenge challenge = challengeQueryDbPort.findSuccessChallengeByIdAndUser_Id(
                    createPostscriptServiceRequest.challengeId(), userId
                ).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_RESOURCE));

        if (postscriptDbPort.findPostscriptByChallenge(challenge).isPresent()) {
            throw new BadRequestException(BadRequestExceptionCode.ALREADY_PARTICIPATE_IN_POSTSCRIPT);
        }

        String photoUrl = commandImagePort.save(createPostscriptServiceRequest.photo(), userId);
        Postscript postscript = Postscript.builder()
                .image(photoUrl)
                .content(createPostscriptServiceRequest.content())
                .isDeleted(false)
                .challenge(challenge)
                .build();
        commandPostscriptDbPort.save(postscript);

        return CreatePostscriptServiceResponse.builder().build();
    }
}
