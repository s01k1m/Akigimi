package com.kangkimleekojangcho.akgimi.user.adapter.in;

import com.kangkimleekojangcho.akgimi.config.ControllerTestSupport;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.CreateFeedRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.FileInputStream;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("GetKakaoLoginUrl HAPPY CASE")
    void GetKakaoLoginUrlHappyCase() throws Exception {



        // given

        // when

        // then
        mockMvc.perform(
                        get("/kakao/loginurl")
                                .header(HttpHeaders.HOST,"http://localhost:3000")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data", Matchers.containsString("http://localhost:3000")));
    }


    @Test
    @DisplayName("GetKakaoLoginUrl BAD CASE")
    void GetKakaoLoginUrlBadCase() throws Exception {
        // then
        mockMvc.perform(
                        get("/kakao/loginurl")
                                .header(HttpHeaders.HOST,"http://akgimi.ddns.net/api")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data", Matchers.containsString("http://akgimi.ddns.net")));
    }
}