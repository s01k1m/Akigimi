package com.kangkimleekojangcho.akgimi.user.adapter.in;

import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.AddDataForPendingUserRequest;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.LoginRequest;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.SignUpRequest;
import com.kangkimleekojangcho.akgimi.user.application.AddDataForPendingUserService;
import com.kangkimleekojangcho.akgimi.user.application.GetIdTokenService;
import com.kangkimleekojangcho.akgimi.user.application.SignUpService;
import com.kangkimleekojangcho.akgimi.user.application.response.AddDataForPendingUserServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.response.LoginServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.LoginService;
import com.kangkimleekojangcho.akgimi.user.application.response.SignUpServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final LoginService loginService;
    private final SignUpService signUpService;
    private final AddDataForPendingUserService addDataForPendingUserService;
    private final GetIdTokenService getIdTokenService;

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


    @GetMapping("/kakao/signup")
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

    private String makeTokenRedirectUrl(String accessToken, String refreshToken, boolean isSignUp) {
        return String.format("%s/access-token=%s&refresh-token=%s&is-signup=%s", kakaoTokenRedirectUrl, accessToken, refreshToken, isSignUp);
    }
    private String makeTokenRedirectUrl(String accessToken, String refreshToken) {
        return String.format("%s/access-token=%s&refresh-token=%s", kakaoTokenRedirectUrl, accessToken, refreshToken);
    }
}
