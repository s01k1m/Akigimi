package com.kangkimleekojangcho.akgimi.sns.adapter.in;

import com.kangkimleekojangcho.akgimi.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ReceiptControllerTest extends ControllerTestSupport {

    private static final String RECEIPT_BASE_URL = "/receipts";

    @DisplayName("[happy] 유저가 올바른 입력값을 가지고 영수증 목록을 요청하면 요청한 사람의 영수증리스트를 반환한다.")
    @Test
    void givenValidInput_whenUserRequestBunchOfReceipt_thenReturnBunchOfFeed() throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("lastReceiptId", "1");
        params.add("numberOfReceipt", "100");

        //when
        ResultActions actions = mockMvc.perform(get(RECEIPT_BASE_URL + "/1").queryParams(params));

        // then
        actions.andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("[bad] 유저가 잘못된 입력값을 가지고 영수증 목록을 요청하면 에러를 반환한다.")
    @MethodSource("generateWrongReceiptRequestData")
    @ParameterizedTest
    void givenValidInput_whenUserRequestBunchOfReceipt_thenThrowsError(Long lastFeedId, Integer numberOfFeed) throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("lastFeedId", lastFeedId == null ? null : lastFeedId.toString());
        params.add("numberOfFeed", numberOfFeed == null ? null : numberOfFeed.toString());

        //when
        ResultActions actions = mockMvc.perform(get(RECEIPT_BASE_URL + "/1").queryParams(params));

        // then
        actions.andDo(print())
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> generateWrongReceiptRequestData() {
        return Stream.of(
                Arguments.of(1L, -1),
                Arguments.of(Long.MAX_VALUE, null),
                Arguments.of(null, 10),
                Arguments.of(null, 0)
        );

    }
}