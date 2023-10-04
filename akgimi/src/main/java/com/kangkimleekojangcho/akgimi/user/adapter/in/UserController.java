package com.kangkimleekojangcho.akgimi.user.adapter.in;

import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.FollowRequest;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.LoginRequest;
import com.kangkimleekojangcho.akgimi.user.adapter.in.response.GetUserInfoServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.*;
import com.kangkimleekojangcho.akgimi.user.application.response.*;
import com.kangkimleekojangcho.akgimi.user.config.KakaoProperties;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final LoginService loginService;
    private final CheckDuplicateNicknameService checkDuplicateNicknameService;
    private final InputNicknameService setNicknameService;
    private final RecommendNicknamesService recommendNicknamesService;
    private final SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;
    private final SetSimplePasswordService setSimplePasswordService;
    private final CheckSimplePasswordService authorizeBySimplePasswordService;
    private final GetUserInfoService getUserInfoService;
    private final ReissueService reissueService;
    private final KakaoProperties kakaoProperties;
    private final FollowUserService followUserService;
    private final GetFriendsInfoService getFriendsInfoService;
    private final SaveUserProfileService saveUserProfileService;
    private final ActivateUserService activateUserService;
    private final CheckUserCanBeActivatedService checkUserCanBeActivatedService;

    @GetMapping("/kakao/loginurl")
    public ResponseEntity<SuccessResponse<String>> getKakaoLoginUrl(HttpServletRequest servletRequest) {
        String redirectApi;
        if(isFromLocalhost(servletRequest)){
            redirectApi = "http://localhost:3000/kakao/oidc/";
        } else{
            redirectApi = "http://akgimi.ddns.net/kakao/oidc/";
        }
        String loginUrl = String.format("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                kakaoProperties.kakaoRestApiKey(), redirectApi);
        return ResponseFactory.success(loginUrl);
    }

    private boolean isFromLocalhost(HttpServletRequest servletRequest) {
        String host = servletRequest.getHeader("Host");
        return host!=null && host.contains("localhost");
    }


    @PostMapping("/kakao/login")
    public ResponseEntity<SuccessResponse<LoginServiceResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginServiceResponse response = loginService.login(request.getIdToken());
        return ResponseFactory.success(response);
    }

    @GetMapping("/user/nickname/duplicate")
    public ResponseEntity<SuccessResponse<Boolean>> checkDuplicateNickname(@RequestParam("nickname") String nickname) {
        validateNickname(nickname);
        boolean response = checkDuplicateNicknameService.check(nickname);
        return ResponseFactory.success(response);
    }

    private static void validateNickname(String nickname) {
        if(nickname ==null || nickname.length()==0) {
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "닉네임 값을 꼭 입력해주세요.");
        }
    }

    @PostMapping("/user/nickname")
    public ResponseEntity<SuccessResponse<Boolean>> setNickname(@RequestParam("nickname") String nickname, HttpServletRequest servletRequest) {
        validateNickname(nickname);
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        setNicknameService.input(userId, nickname);
        return ResponseFactory.success(true);
    }

    @GetMapping("/user/nickname/recommend")
    public ResponseEntity<SuccessResponse<RecommendNicknamesServiceResponse>> recommendNicknames(HttpServletRequest servletRequest) {
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        RecommendNicknamesServiceResponse response = recommendNicknamesService.recommend(userId);
        return ResponseFactory.success(response);
    }

    @PostMapping("/user/password/simple")
    public void setSimplePassword(@RequestParam String simplePassword, HttpServletRequest servletRequest) {
        validateSimplePassword(simplePassword);
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        setSimplePasswordService.set(userId, simplePassword);
    }

    private static void validateSimplePassword(String simplePassword) {
        if (simplePassword == null || simplePassword.length()==0) {
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "간편 비밀번호를 입력해주세요.");
        }
    }

    @PostMapping("/user/password/simple/check")
    public ResponseEntity<SuccessResponse<AuthorizeBySimplePasswordResponse>> authorizeBySimplePassword(@RequestParam String simplePassword,
                                                                                                        HttpServletRequest servletRequest) {
        validateSimplePassword(simplePassword);
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        AuthorizeBySimplePasswordResponse response = authorizeBySimplePasswordService.authorize(userId, simplePassword);
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

    @PostMapping("/user/profile")
    public void uploadProfile(@RequestParam("files") List<MultipartFile> files, HttpServletRequest servletRequest) {
        if(files.size()!=1){
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "한 개의 이미지를 업로드해야 합니다.");
        }
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        saveUserProfileService.save(userId, files.get(0));
    }

    @GetMapping("/user/can-activate")
    public ResponseEntity<SuccessResponse<CheckUserCanBeActivatedServiceResponse>> canActivate(HttpServletRequest servletRequest) {
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        CheckUserCanBeActivatedServiceResponse response = checkUserCanBeActivatedService.check(userId);
        return ResponseFactory.success(response);
    }

    @PostMapping("/user/activate")
    public ResponseEntity<SuccessResponse<ActivateUserServiceResponse>> activateUser(HttpServletRequest servletRequest) {
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        ActivateUserServiceResponse response = activateUserService.activate(userId);
        return ResponseFactory.success(response);
    }

    @GetMapping("/friends/search")
    public ResponseEntity<SuccessResponse<FriendsServiceResponse>> searchFriends(@RequestParam("nickname") String nickname, HttpServletRequest servletRequest){
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        FriendsServiceResponse response = getFriendsInfoService.search(userId,nickname);
        return ResponseFactory.success(response);
    }
}
