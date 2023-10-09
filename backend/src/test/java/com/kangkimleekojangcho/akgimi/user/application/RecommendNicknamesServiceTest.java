package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.config.ServiceIntegrationTestSupport;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.RecommendNicknamesServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoNickname;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RecommendNicknamesServiceTest extends ServiceIntegrationTestSupport {
    @Autowired
    private RecommendNicknamesService recommendNicknamesService;

    @Autowired
    private CommandUserDbPort commandUserDbPort;

    @Test
    @DisplayName("카카오 닉네임을 가지고서 닉네임을 추천한다.")
    void recommend1() {
        // given
        User user = User.builder()
                .id(1L)
                .kakaoProfileNickname(new KakaoNickname("홍길동")).build();
        user = commandUserDbPort.save(user);

        // when
        RecommendNicknamesServiceResponse response = recommendNicknamesService.recommend(user.getId());
        // then
        Assertions.assertThat(response.getNickname()).contains("홍길동");
    }

    @Test
    @DisplayName("너무 긴 카카오 닉네임을 가진다면 (길이 3이상), 길이가 3이 되도록 자른 후, 닉네임 추천에 활용한다.")
    void recommend2() {
        // given
        User user = User.builder()
                .id(1L)
                .kakaoProfileNickname(new KakaoNickname("ABCDEF")).build();
        user = commandUserDbPort.save(user);

        // when
        RecommendNicknamesServiceResponse response = recommendNicknamesService.recommend(user.getId());
        // then
        Assertions.assertThat(response.getNickname()).contains("ABC");
        Assertions.assertThat(response.getNickname()).doesNotContain("ABCD");
    }
}