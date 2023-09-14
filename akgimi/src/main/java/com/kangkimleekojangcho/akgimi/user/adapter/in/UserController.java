package com.kangkimleekojangcho.akgimi.user.adapter.in;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.AddDataForPendingUserRequest;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.LoginRequest;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.SignUpRequest;
import com.kangkimleekojangcho.akgimi.user.application.*;
import com.kangkimleekojangcho.akgimi.user.application.response.AddDataForPendingUserServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.response.LoginServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.response.RecommendNicknamesServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.response.SignUpServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.predicate.BooleanExpressionPredicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @Value("${kakao.redirection-url}")
    String kakaoRedirectUrl;
    @Value("${kakao.rest-api-key}")
    String kakaoRestApiKey;
    @Value("${kakao.token-redirect-url}")
    String kakaoTokenRedirectUrl;

    @GetMapping("/kakao/loginurl")
    public ResponseEntity<SuccessResponse<String>> getKakaoLoginUrl() {
        String loginUrl = String.format("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                kakaoRestApiKey, kakaoRedirectUrl);
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

    @PostMapping("/user/extrainfo")
    public ResponseEntity<SuccessResponse<AddDataForPendingUserServiceResponse>> addDataForPendingUser(@RequestBody @Valid AddDataForPendingUserRequest request, HttpServletRequest servletRequest) {
        Long userId = ((JwtToken) servletRequest.getAttribute("accessToken")).getUserId();
        AddDataForPendingUserServiceResponse response = addDataForPendingUserService.addDataForPendingUser(userId, request.toServiceRequest());
        return ResponseFactory.success(response);
    }

    @GetMapping("/user/nickname/duplicate")
    public ResponseEntity<SuccessResponse<Boolean>> checkDuplicateNickname(@RequestParam("nickname") String nickname) {
        boolean response = checkDuplicateNicknameService.check(nickname);
        return ResponseFactory.success(response);
    }

    @PostMapping("/user/nickname")
    public ResponseEntity<SuccessResponse<Boolean>> inputNickname(@RequestParam("nickname") String nickname, HttpServletRequest servletRequest) {
        long userId = ((JwtToken) servletRequest.getAttribute("accessToken")).getUserId();
        if(nickname==null) throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT);
        inputNicknameService.input(userId, nickname);
        return ResponseFactory.success(true);
    }

    @GetMapping("/user/nickname/recommend")
    public ResponseEntity<SuccessResponse<RecommendNicknamesServiceResponse>> recommendNicknames(HttpServletRequest servletRequest) {
        long userId = ((JwtToken) servletRequest.getAttribute("accessToken")).getUserId();
        RecommendNicknamesServiceResponse response = recommendNicknamesService.recommend(userId);
        return ResponseFactory.success(response);
    }
}
