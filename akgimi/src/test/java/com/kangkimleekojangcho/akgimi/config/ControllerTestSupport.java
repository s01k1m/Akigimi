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
import com.kangkimleekojangcho.akgimi.sns.application.*;
import com.kangkimleekojangcho.akgimi.user.adapter.in.UserController;
import com.kangkimleekojangcho.akgimi.user.application.*;
import com.kangkimleekojangcho.akgimi.user.config.KakaoProperties;
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
        PostscriptController.class,
        UserController.class
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

    @MockBean
    protected CancelLikeService cancelLikeService;

    @MockBean
    private LoginService loginService;
    @MockBean
    private CheckDuplicateNicknameService checkDuplicateNicknameService;
    @MockBean
    private InputNicknameService setNicknameService;
    @MockBean
    private  RecommendNicknamesService recommendNicknamesService;
    @MockBean
    private SetSimplePasswordService setSimplePasswordService;
    @MockBean
    private  CheckSimplePasswordService authorizeBySimplePasswordService;
    @MockBean
    private GetUserInfoService getUserInfoService;
    @MockBean
    private  ReissueService reissueService;
    @MockBean
    private KakaoProperties kakaoProperties;
    @MockBean
    private FollowUserService followUserService;
    @MockBean
    private GetFriendsInfoService getFriendsInfoService;
    @MockBean
    private SaveUserProfileService saveUserProfileService;
    @MockBean
    private ActivateUserService activateUserService;
    @MockBean
    private CheckUserCanBeActivatedService checkUserCanBeActivatedService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected SubtractUserIdFromAccessTokenService userIdFromAccessTokenService;
}
