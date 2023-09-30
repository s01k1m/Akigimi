package com.kangkimleekojangcho.akgimi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kangkimleekojangcho.akgimi.challenge.adapter.in.PostscriptController;
import com.kangkimleekojangcho.akgimi.challenge.application.CreatePostscriptService;
import com.kangkimleekojangcho.akgimi.challenge.application.GetBunchOfPostscriptService;
import com.kangkimleekojangcho.akgimi.common.application.MattermostSender;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.config.WebSecurityConfig;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.FeedController;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.ReceiptController;
import com.kangkimleekojangcho.akgimi.sns.application.CreateFeedService;
import com.kangkimleekojangcho.akgimi.sns.application.GetBunchOfFeedWrittenByFollowerRequestService;
import com.kangkimleekojangcho.akgimi.sns.application.GetBunchOfReceiptService;
import com.kangkimleekojangcho.akgimi.sns.application.MarkLikeToFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        FeedController.class,
        ReceiptController.class,
        PostscriptController.class
}, excludeFilters = { //!Added!
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                SecurityConfig.class,
                WebSecurityConfig.class
        })}
)
@AutoConfigureMockMvc(addFilters = false) // 필터 비활성화
public abstract class ControllerTestSupport {

    @MockBean
    protected MattermostSender mattermostSender;

    @MockBean
    protected GetBunchOfReceiptService getBunchOfReceiptService;
    @MockBean
    protected SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;
    @MockBean
    protected CreateFeedService createFeedService;
    @MockBean
    protected GetBunchOfFeedWrittenByFollowerRequestService getBunchOfFeedWrittenByFollowerRequestService;
    @MockBean
    protected CreatePostscriptService createPostscriptService;
    @MockBean
    protected GetBunchOfPostscriptService getBunchOfPostscriptService;
    @MockBean
    protected MarkLikeToFeedService markLikeToFeedService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected SubtractUserIdFromAccessTokenService userIdFromAccessTokenService;
}
