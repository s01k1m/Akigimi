package com.kangkimleekojangcho.akgimi.challenge.adapter.in;

import com.kangkimleekojangcho.akgimi.config.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.FileInputStream;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostscriptControllerTest extends ControllerTestSupport {

    private static final String POSTSCRIPT_BASE_URL = "/postscripts";

    @DisplayName("[happy]리뷰 작성 요청을 정확하게 하면 올바른 응답을 처리한다.")
    @Test
    void givenValidPostscriptData_whenRequestCreatePostscript_thenReturnValidResult() throws Exception {

        //given
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/test/test.png"));


        //when
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("challengeId", "1");
        params.add("content", "content");


        //when then
        mockMvc.perform(
                multipart(POSTSCRIPT_BASE_URL)
                        .file("photo", file.getBytes())
                        .params(params)
        ).andExpect(status().isOk());
        //then
    }

    @DisplayName("[bad] 리뷰 요청 시 유저가 잘못된 입력값을 가지고 요청하면 에러를 반환한다.")
    @MethodSource("generateWrongCreatePostscriptRequestData")
    @ParameterizedTest
    void givenValidInput_whenUserRequestBunchOfFeed_thenThrowsError(Long challengeId, String content) throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/test/test.png"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("challengeId", challengeId == null ? null : challengeId.toString());
        params.add("content", content);
        //when
        ResultActions actions = mockMvc.perform(multipart(POSTSCRIPT_BASE_URL).file(file).queryParams(params));

        // then
        actions.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("[happy]리뷰 리스트 요청시 올바른 값이 들어오면 리스트를 반환한다.")
    @Test
    void givenValidRequest_whenRequestBunchOfPostscript_thenReturnValidData() throws Exception {
        //given
        MultiValueMap<String, String> bunchOfPostscriptRequest = new LinkedMultiValueMap<>();
        bunchOfPostscriptRequest.add("lastPostscriptId", "1");
        bunchOfPostscriptRequest.add("numberOfPostscript", "2");
        bunchOfPostscriptRequest.add("productId", "1");
        //when then
        mockMvc.perform(
                get("/postscripts").params(bunchOfPostscriptRequest)
        ).andExpect(status().isOk());
        //then
    }

    @DisplayName("[bad] 리뷰 리스트 요청시 유저가 잘못된 입력값을 가지고 요청하면 에러를 반환한다.")
    @MethodSource("generateWrongBunchOfPostscriptRequestData")
    @ParameterizedTest
    void givenInvalidInput_whenRequestBunchOfPostscript_thenThrowsError(
            Long lastPostscriptId, Integer numberOfPostscript, Long productId) throws Exception {
        MultiValueMap<String, String> bunchOfPostscriptRequest = new LinkedMultiValueMap<>();
        bunchOfPostscriptRequest.add("lastPostscriptId", lastPostscriptId.toString());
        bunchOfPostscriptRequest.add("numberOfPostscript", numberOfPostscript.toString());
        bunchOfPostscriptRequest.add("productId", productId.toString());
        //when then
        mockMvc.perform(
                get("/postscripts").params(bunchOfPostscriptRequest)
        ).andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> generateWrongCreatePostscriptRequestData() {
        return Stream.of(
                Arguments.of(null, "content"),
                Arguments.of(1L, null)
        );
    }

    private static Stream<Arguments> generateWrongBunchOfPostscriptRequestData() {
        return Stream.of(
                Arguments.of(1L, -1, 1L),
                Arguments.of(1L, 101, 1L),
                Arguments.of(1L, 50, -1L)
        );
    }
}
