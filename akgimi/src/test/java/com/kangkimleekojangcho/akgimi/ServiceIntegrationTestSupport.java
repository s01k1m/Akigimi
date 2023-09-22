package com.kangkimleekojangcho.akgimi;


import com.kangkimleekojangcho.akgimi.common.config.S3Config;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandFeedImagePort;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserProfileImagePort;
import com.kangkimleekojangcho.akgimi.user.config.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@RequiredArgsConstructor
public abstract class ServiceIntegrationTestSupport {

    static {
//        List<String> portBindings = new ArrayList<>();
//        portBindings.add("6380:6379");
//
//        GenericContainer<?> redis =
//                new GenericContainer<>(DockerImageName.parse("redis:7.2.1-alpine"));
//        redis.setPortBindings(portBindings);
//        redis.start();
//        //mysql 추가하기
    }

    @MockBean
    protected CommandUserProfileImagePort commandUserProfileImagePort;

    @MockBean
    protected CommandFeedImagePort commandFeedImagePort;

    @MockBean
    protected S3Config s3Config;

    @MockBean
    protected KakaoProperties kakaoProperties;

}
