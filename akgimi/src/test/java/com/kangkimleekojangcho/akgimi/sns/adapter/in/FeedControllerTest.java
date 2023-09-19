package com.kangkimleekojangcho.akgimi.sns.adapter.in;

import com.kangkimleekojangcho.akgimi.ControllerTestSupport;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.sns.application.CreateFeedService;
import com.kangkimleekojangcho.akgimi.sns.application.GetBunchOfFeedWrittenByFollowerRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FeedControllerTest extends ControllerTestSupport {

    @MockBean
    private CreateFeedService createFeedService;
    @MockBean
    private GetBunchOfFeedWrittenByFollowerRequestService getBunchOfFeedWrittenByFollowerRequestService;
    @MockBean
    private SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;
//
//    @DisplayName("[happy]유저가 올바른 정보를 입력했을 때 정확한 파싱결과를 도출한다.")
//    @Test
//    void parsingRequestTest() throws Exception {
//        MockMultipartFile file = new MockMultipartFile("image",
//                "test.png",
//                "image/png",
//                new FileInputStream("src/test/resources/test/test.png"));
//
//        //given
//        CreateFeedRequest request = CreateFeedRequest.builder()
//                .notPurchasedItem("끼밍템")
//                .akgimiPlace("place")
//                .content("content")
//                .isPublic(true)
//                .photo(file)
//                .saving(11441L)
//                .build();
//
//        //when then
//        mockMvc.perform(
//                multipart("/feed")
//                        .file(file)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .requestAttr("dto", request)
//        ).andExpect(MockMvcResultMatchers.status().isOk());
//
//    }
//
//    @ParameterizedTest
//    @DisplayName("[bad]유저가 잘못된 정보를 입력했을 때 올바른 오류를 반환한다.")
//    @MethodSource("generateData")
//    void requestValidationTest(String notPurchasedItem, Long saving,
//                               String akgimiPlace, String content, Boolean isPublic) throws Exception {
//        //given
//        MockMultipartFile file = new MockMultipartFile("image",
//                "test.png",
//                "image/png",
//                new FileInputStream("src/test/resources/test/test.png"));
//        CreateFeedRequest request = CreateFeedRequest.builder()
//                .notPurchasedItem(notPurchasedItem)
//                .akgimiPlace(akgimiPlace)
//                .content(content)
//                .isPublic(isPublic)
//                .photo(file)
//                .saving(saving)
//                .build();
//
//        //when then
//        mockMvc.perform(
//                multipart("/feed")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .requestAttr("dto", request)
//        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }
//
//    private static Stream<Arguments> generateData() {
//        return Stream.of(
//                Arguments.of(" ", 1000L, "akgimiPlace", "content", true),
//                Arguments.of("notPurchasedItem", -10L, "akgimiPlace", "content", true),
//                Arguments.of("   ", 1000L, "   ", "content", true)
//        );
//    }

    @DisplayName("[happy] 유저가 올바른 입력값을 주었을 때 팔로잉한 사람들의 피드리스트를 반환한다.")
    @Test
    void givenValidInput_whenUserRequestBunchOfFeed_thenReturnBunchOfFeed() throws Exception {
        //given
        HttpServletRequest servletRequest = new MockHttpServletRequest();
        GetBunchOfFeedWrittenByFollowerRequest request = GetBunchOfFeedWrittenByFollowerRequest.builder()
                .lastFeedId(1L)
                .numberOfFeed(5)
                .build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("lastFeedId", "1");
        params.add("numberOfFeed", "2");

        //when
        ResultActions actions = mockMvc.perform(get("/feed").queryParams(params));

        // then
        actions.andDo(print())
                .andExpect(status().isOk());
    }

}