package com.kangkimleekojangcho.akgimi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kangkimleekojangcho.akgimi.common.application.MattermostSender;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.config.WebSecurityConfig;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.FeedController;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.ReceiptController;
import com.kangkimleekojangcho.akgimi.sns.application.CreateFeedService;
import com.kangkimleekojangcho.akgimi.sns.application.GetBunchOfFeedWrittenByFollowerRequestService;
import com.kangkimleekojangcho.akgimi.sns.application.GetBunchOfReceiptService;
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
        ReceiptController.class
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

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected SubtractUserIdFromAccessTokenService userIdFromAccessTokenService;
}
