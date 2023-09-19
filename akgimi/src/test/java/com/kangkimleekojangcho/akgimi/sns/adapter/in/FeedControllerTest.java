package com.kangkimleekojangcho.akgimi.sns.adapter.in;

import com.kangkimleekojangcho.akgimi.ControllerTestSupport;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.CreateFeedRequest;
import com.kangkimleekojangcho.akgimi.sns.application.CreateFeedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;
import java.util.stream.Stream;

class FeedControllerTest extends ControllerTestSupport {

    @MockBean
    private CreateFeedService createFeedService;
    @MockBean
    private SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;

    @DisplayName("[happy]유저가 올바른 정보를 입력했을 때 정확한 파싱결과를 도출한다.")
    @Test
    void parsingRequestTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/test/test.png"));

        //given
        CreateFeedRequest request = CreateFeedRequest.builder()
                .notPurchasedItem("끼밍템")
                .akgimiPlace("place")
                .content("content")
                .isOpen(true)
                .photo(file)
                .saving(11441L)
                .build();

        //when then
        mockMvc.perform(
                MockMvcRequestBuilders.multipart("/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("dto", request)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @ParameterizedTest
    @DisplayName("[bad]유저가 잘못된 정보를 입력했을 때 올바른 오류를 반환한다.")
    @MethodSource("generateData")
    void requestValidationTest(String notPurchasedItem, Long saving, String akgimiPlace, String content, Boolean isOpen) throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/test/test.png"));
        CreateFeedRequest request = CreateFeedRequest.builder()
                .notPurchasedItem(notPurchasedItem)
                .akgimiPlace(akgimiPlace)
                .content(content)
                .isOpen(isOpen)
                .photo(file)
                .saving(saving)
                .build();

        //when then
        //when then
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.multipart("/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("dto", request)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(" ", 1000L, "akgimiPlace", "content", true),
                Arguments.of("notPurchasedItem", -10L, "akgimiPlace", "content", true),
                Arguments.of("   ", 1000L, "   ", "content", true)
        );
    }
}