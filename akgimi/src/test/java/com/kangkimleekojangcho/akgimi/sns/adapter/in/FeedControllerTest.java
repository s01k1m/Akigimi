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

import java.util.stream.Stream;

import static com.kangkimleekojangcho.akgimi.global.response.SuccessResponseMessage.message;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FeedControllerTest extends ControllerTestSupport {

    @MockBean
    private CreateFeedService createFeedService;
    @MockBean
    private SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;

    @DisplayName("[happy]유저가 올바른 정보를 입력했을 때 정확한 파싱결과를 도출한다.")
    @Test
    void parsingRequestTest() throws Exception {
        //given
        CreateFeedRequest request = CreateFeedRequest.builder()
                .meaningItem("미닝템")
                .akgimPlace("place")
                .content("content")
                .isOpen(true)
                .photo("https://hello.world")
                .saving(11441L)
                .build();

        //when then
        mockMvc.perform(
                        post("/feed")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(message.getValue()));
    }

    @ParameterizedTest
    @DisplayName("[bad]유저가 잘못된 정보를 입력했을 때 올바른 오류를 반환한다.")
    @MethodSource("generateData")
    void requestValidationTest(String meaningItem, Long saving, String akgimPlace,
                               String photo, String content, Boolean isOpen) throws Exception {
        System.out.println(meaningItem);
        //given
        CreateFeedRequest request = CreateFeedRequest.builder()
                .meaningItem(meaningItem)
                .akgimPlace(akgimPlace)
                .content(content)
                .isOpen(isOpen)
                .photo(photo)
                .saving(saving)
                .build();

        //when then
        mockMvc.perform(
                        post("/feed")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(" ", 1000L, "akgimPlace", "photo", "content", true),
                Arguments.of("notPurchasedItem", -10L, "akgimPlace", "photo", "content", true),
                Arguments.of("   ", 1000L, "   ", "photo", "content", true)
        );
    }
}