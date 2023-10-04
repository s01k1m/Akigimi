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
    @DisplayName("localhost:3000에서 카카오 로그인 URL을 요청하는 경우, localhost:3000이 포함된 loginUrl, redirectUrl을 리턴한다.")
    void getKakaoUrl1() throws Exception {



        // given

        // when

        // then
        mockMvc.perform(
                        get("/kakao/loginurl")
                                .header(HttpHeaders.HOST, "http://localhost:3000")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.loginUrl", Matchers.containsString("http://localhost:3000")))
                .andExpect(jsonPath("$.data.redirectUrl", Matchers.containsString("http://localhost:3000")));
    }


    @Test
    @DisplayName("akgimi.ddns.net에서 카카오 로그인 URL을 요청하는 경우, akgimi.ddns.net이 포함된 loginUrl, redirectUrl을 리턴한다.")
    void getKakaoUrl2() throws Exception {
        // then
        mockMvc.perform(
                        get("/kakao/loginurl")
                                .header(HttpHeaders.HOST,"http://akgimi.ddns.net/api")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.loginUrl", Matchers.containsString("http://akgimi.ddns.net")))
                .andExpect(jsonPath("$.data.redirectUrl", Matchers.containsString("http://akgimi.ddns.net")));
    }
}