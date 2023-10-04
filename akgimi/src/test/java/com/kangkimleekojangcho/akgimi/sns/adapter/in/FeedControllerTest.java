package com.kangkimleekojangcho.akgimi.sns.adapter.in;

import com.kangkimleekojangcho.akgimi.config.ControllerTestSupport;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.CreateFeedRequest;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.MarkLikeToFeedRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.FileInputStream;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FeedControllerTest extends ControllerTestSupport {

    private static final String FEED_BASE_URL = "/feeds";

    @DisplayName("[happy]피드 생성 시 유저가 올바른 정보를 입력했을 때 정확한 파싱결과를 도출한다.")
    @Test
    void parsingRequestTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/test/test.png"));
        //given
        CreateFeedRequest request = CreateFeedRequest.builder()
                .notPurchasedItem("안샀던 아이템")
                .akgimiPlace("두썸 플레이스")
                .content("content")
                .isPublic(true)
                .photo(file)
                .saving(11441L)
                .build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("notPurchasedItem", "dd");
        params.add("akgimiPlace", "두썸 플레이스");
        params.add("content", "content");
        params.add("isPublic", "false");
        params.add("saving", "11441");


        //when then
        mockMvc.perform(
                multipart(FEED_BASE_URL)
                        .file("photo", file.getBytes())
                        .params(params)
        ).andExpect(status().isOk());
    }

    @ParameterizedTest
    @DisplayName("[bad]피드 생성 요청 시 유저가 잘못된 정보를 입력했을 때 올바른 오류를 반환한다.")
    @MethodSource("generateData")
    void requestValidationTest(String notPurchasedItem, Long saving,
                               String akgimiPlace, String content,
                               Boolean isPublic, MockMultipartFile file) throws Exception {
        //given
        CreateFeedRequest request = CreateFeedRequest.builder()
                .notPurchasedItem(notPurchasedItem)
                .akgimiPlace(akgimiPlace)
                .content(content)
                .isPublic(isPublic)
                .photo(file)
                .saving(saving)
                .build();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("notPurchasedItem", notPurchasedItem);
        params.add("akgimiPlace", akgimiPlace);
        params.add("content", content);
        params.add("isPublic", isPublic == null ? null : Boolean.toString(isPublic));
        params.add("saving", saving == null ? null : saving.toString());

        //when then
        mockMvc.perform(
                        multipart(FEED_BASE_URL)
                                .file("photo", file.getBytes())
                                .params(params)
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> generateData() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/test/test.png"));
        return Stream.of(
                Arguments.of(" ", 1000L, "akgimiPlace", "content", true, file),
                Arguments.of("notPurchasedItem", -10L, "akgimiPlace", "content", true, file),
                Arguments.of("   ", 1000L, "   ", "content", true, file),
                Arguments.of("   ", 1000L, "   ", "content", null, file)
        );
    }

    @DisplayName("[happy] 피드 요청 시 유저가 올바른 입력값을 가지고 있으면 팔로잉한 사람들의 피드리스트를 반환한다.")
    @Test
    void givenValidInput_whenUserRequestBunchOfFeed_thenReturnBunchOfFeed() throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("lastFeedId", "1");
        params.add("numberOfFeed", "2");

        //when
        ResultActions actions = mockMvc.perform(get(FEED_BASE_URL).queryParams(params));

        // then
        actions.andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[bad] 피드 요청 시 유저가 잘못된 입력값을 가지고 요청하면 에러를 반환한다.")
    @MethodSource("generateWrongFeedRequestData")
    @ParameterizedTest
    void givenInvalidInput_whenUserRequestBunchOfFeed_thenThrowsError(Long lastFeedId, Integer numberOfFeed) throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("lastFeedId", lastFeedId == null ? null : lastFeedId.toString());
        params.add("numberOfFeed", numberOfFeed == null ? null : numberOfFeed.toString());

        //when
        ResultActions actions = mockMvc.perform(get(FEED_BASE_URL).queryParams(params));

        // then
        actions.andDo(print())
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> generateWrongFeedRequestData() {
        return Stream.of(
                Arguments.of(1L, -1),
                Arguments.of(1L, null),
                Arguments.of(null, 10)
        );
    }

    @DisplayName("[happy] like 요청 시 유저가 올바른 입력값을 보내면 피드에 좋아요를 수행한다.")
    @Test
    void givenValidInput_whenUserRequestMarkLikeToFeed_thenExecuteLike() throws Exception {
        //given
        MarkLikeToFeedRequest markLikeToFeedRequest = MarkLikeToFeedRequest
                .builder()
                .feedId(1L)
                .build();

        //when
        ResultActions actions = mockMvc.perform(post(FEED_BASE_URL+"/likes").content(
                objectMapper.writeValueAsString(markLikeToFeedRequest))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[bad] 좋아요 요청 시 유저가 잘못된 feedId 값을 주는 경우 에러를 반환한다")
    @ValueSource(longs = {-1})
    @ParameterizedTest
    void givenInvalidInput_whenUserRequestMarkLikeToFeed_thenThrowsError(Long feedId) throws Exception {
        //given
        MarkLikeToFeedRequest markLikeToFeedRequest = MarkLikeToFeedRequest
                .builder()
                .feedId(feedId)
                .build();
        //when
        ResultActions actions = mockMvc.perform(post(FEED_BASE_URL+"/likes").content(
                objectMapper.writeValueAsString(markLikeToFeedRequest))
                .contentType(MediaType.APPLICATION_JSON));
        // then
        actions.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("[happy] like 취소 요청 시 유저가 올바른 입력값을 보내면 피드에 좋아요 취소를 수행한다.")
    @Test
    void givenValidFeed_whenCancelLike_thenCancel() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(delete(FEED_BASE_URL+"/likes")
                .param("feedId", "1"));

        // then
        actions.andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[bad] 좋아요 취소 요청 시 유저가 잘못된 feedId 값을 주는 경우 에러를 반환한다")
    @ValueSource(longs = {-1})
    @ParameterizedTest
    void givenInvalidInput_whenCancelFeed_thenThrowsError(Long feedId) throws Exception {
        //given
        //when
        ResultActions actions = mockMvc.perform(delete(FEED_BASE_URL+"/likes")
                .param("feedId", feedId.toString()));

        // then
        actions.andDo(print())
                .andExpect(status().isBadRequest());
    }
}