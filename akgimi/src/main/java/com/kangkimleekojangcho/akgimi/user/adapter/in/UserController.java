package com.kangkimleekojangcho.akgimi.user.adapter.in;

import com.kangkimleekojangcho.akgimi.bank.application.GenerateWithdrawalAccountService;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.FollowRequest;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.LoginRequest;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.SignUpRequest;
import com.kangkimleekojangcho.akgimi.user.adapter.in.response.GetUserInfoServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.*;
import com.kangkimleekojangcho.akgimi.user.application.response.*;
import com.kangkimleekojangcho.akgimi.user.config.KakaoProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final LoginService loginService;
    private final SignUpService signUpService;
    private final AddDataForPendingUserService addDataForPendingUserService;
    private final GetIdTokenService getIdTokenService;
    private final CheckDuplicateNicknameService checkDuplicateNicknameService;
    private final InputNicknameService inputNicknameService;
    private final RecommendNicknamesService recommendNicknamesService;
    private final GenerateWithdrawalAccountService generateWithdrawalAccountService;
    private final SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;
    private final SetSimplePasswordService setSimplePasswordService;
    private final CheckSimplePasswordService checkSimplePasswordService;
    private final GetUserInfoService getUserInfoService;
    private final ReissueService reissueService;
    private final KakaoProperties kakaoProperties;
    private final FollowUserService followUserService;
    private final GetFriendsInfoService getFriendsInfoService;

    @GetMapping("/kakao/loginurl")
    public ResponseEntity<SuccessResponse<String>> getKakaoLoginUrl() {
        String loginUrl = String.format("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                kakaoProperties.kakaoRestApiKey(), kakaoProperties.kakaoRedirectUrl());
        return ResponseFactory.success(loginUrl);
    }


    @PostMapping("/kakao/login")
    public ResponseEntity<SuccessResponse<LoginServiceResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginServiceResponse response = loginService.login(request.getIdToken());
        return ResponseFactory.success(response);
    }


    @PostMapping("/kakao/signup")
    public ResponseEntity<SuccessResponse<SignUpServiceResponse>> signup(@RequestBody @Valid SignUpRequest request) {
        SignUpServiceResponse response = signUpService.signUp(request.getIdToken());
        return ResponseFactory.success(response);
    }

    @GetMapping("/user/nickname/duplicate")
    public ResponseEntity<SuccessResponse<Boolean>> checkDuplicateNickname(@RequestParam("nickname") String nickname) {
        boolean response = checkDuplicateNicknameService.check(nickname);
        return ResponseFactory.success(response);
    }

    @PostMapping("/user/nickname")
    public ResponseEntity<SuccessResponse<Boolean>> inputNickname(@RequestParam("nickname") String nickname, HttpServletRequest servletRequest) {
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        if (nickname == null) throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT);
        inputNicknameService.input(userId, nickname);
        return ResponseFactory.success(true);
    }

    @GetMapping("/user/nickname/recommend")
    public ResponseEntity<SuccessResponse<RecommendNicknamesServiceResponse>> recommendNicknames(HttpServletRequest servletRequest) {
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        RecommendNicknamesServiceResponse response = recommendNicknamesService.recommend(userId);
        return ResponseFactory.success(response);
    }

    @GetMapping("/user/generated-withdrawal-account")
    public ResponseEntity<SuccessResponse<GenerateWithdrawalAccountServiceResponse>> generateWithdrawalAccount(HttpServletRequest servletRequest) {
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        GenerateWithdrawalAccountServiceResponse response = generateWithdrawalAccountService.generate(userId);
        return ResponseFactory.success(response);
    }

    @PostMapping("/user/password/simple")
    public void setSimplePassword(@RequestParam String simplePassword, HttpServletRequest servletRequest) {
        if (simplePassword == null) {
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "간편 비밀번호를 입력해주세요.");
        }
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        setSimplePasswordService.set(userId, simplePassword);
    }

    @PostMapping("/user/password/simple/check")
    public ResponseEntity<SuccessResponse<Boolean>> checkSimplePassword(@RequestParam String simplePassword,
                                                                        HttpServletRequest servletRequest) {
        if (simplePassword == null) {
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "간편 비밀번호를 입력해주세요.");
        }
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        boolean response = checkSimplePasswordService.check(userId, simplePassword);
        return ResponseFactory.success(response);
    }

    @GetMapping("/user/info")
    public ResponseEntity<SuccessResponse<GetUserInfoServiceResponse>> getUserInfo(HttpServletRequest servletRequest) {
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        GetUserInfoServiceResponse response = getUserInfoService.get(userId);
        return ResponseFactory.success(response);
    }

    @PostMapping("/user/reissue")
    public ResponseEntity<SuccessResponse<ReissueServiceResponse>> reissue(@RequestParam("refreshToken") String refreshToken) {
        ReissueServiceResponse response = reissueService.reissue(refreshToken);
        return ResponseFactory.success(response);
    }

    @PostMapping("/friends")
    public ResponseEntity<SuccessResponse<Boolean>> followUser(@RequestBody FollowRequest request, HttpServletRequest servletRequest) {
        long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        boolean response = followUserService.followUser(userId, request.getFollowee());
        return ResponseFactory.success(response);
    }

    @GetMapping("/friends")
    public ResponseEntity<SuccessResponse<FriendsServiceResponse>> getFriendsInfo(@RequestParam("friendType") String friendType, HttpServletRequest servletRequest){
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        FriendsServiceResponse response = getFriendsInfoService.get(userId,friendType);
        return ResponseFactory.success(response);
    }
}
