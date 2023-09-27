package com.kangkimleekojangcho.akgimi.challenge.adapter.in;

import com.kangkimleekojangcho.akgimi.challenge.adapter.in.request.CreatePostscriptRequest;
import com.kangkimleekojangcho.akgimi.config.ControllerTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostscriptControllerTest extends ControllerTestSupport {

    private static final String POSTSCRIPT_BASE_URL = "/postscripts";

    @Test
    void test() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/test/test.png"));

        //given
        CreatePostscriptRequest request
                = CreatePostscriptRequest.builder()
                .challengeId(1L)
                .content("contentTest")
                .multipartFile(file)
                .build();



        //when
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("postscriptId", "1");
        params.add("content", "content");


        //when then
        mockMvc.perform(
                multipart(POSTSCRIPT_BASE_URL)
                        .file("photo", file.getBytes())
                        .params(params)
        ).andExpect(status().isOk());
        //then
    }


}