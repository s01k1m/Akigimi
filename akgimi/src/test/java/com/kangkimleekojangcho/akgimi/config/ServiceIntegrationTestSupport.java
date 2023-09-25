package com.kangkimleekojangcho.akgimi.config;


import com.kangkimleekojangcho.akgimi.common.config.S3Config;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandFeedImagePort;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserProfileImagePort;
import com.kangkimleekojangcho.akgimi.user.config.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@RequiredArgsConstructor
public abstract class ServiceIntegrationTestSupport {

    @MockBean
    protected CommandUserProfileImagePort commandUserProfileImagePort;

    @MockBean
    protected CommandFeedImagePort commandFeedImagePort;

    @MockBean
    protected CommandUserProfileImagePort commandUserProfileImagePort;

    @MockBean
    protected S3Config s3Config;

    @MockBean
    protected KakaoProperties kakaoProperties;

}
