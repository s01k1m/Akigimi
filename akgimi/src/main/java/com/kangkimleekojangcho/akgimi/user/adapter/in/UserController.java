package com.kangkimleekojangcho.akgimi.user.adapter.in;

import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.user.adapter.in.request.AddDataForPendingUserRequest;
import com.kangkimleekojangcho.akgimi.user.application.AddDataForPendingUserService;
import com.kangkimleekojangcho.akgimi.user.application.SignUpService;
import com.kangkimleekojangcho.akgimi.user.application.response.AddDataForPendingUserServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.response.LoginServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.LoginService;
import com.kangkimleekojangcho.akgimi.user.application.response.SignUpServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final LoginService loginService;
    private final SignUpService signUpService;
    private final AddDataForPendingUserService addDataForPendingUserService;

    @Value("${kakao.login-redirection-url}")
    String kakaoLoginRedirectionUrl;
    @Value("${kakao.signup-redirection-url}")
    String kakaoSignUpRedirectionUrl;
    @Value("${kakao.rest-api-key}")
    String kakaoRestApiKey;

    @GetMapping("/kakao/loginurl")
    public ResponseEntity<SuccessResponse<String>> getKakaoLoginUrl() {
        String loginUrl = String.format("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                kakaoRestApiKey, kakaoLoginRedirectionUrl);
        return ResponseFactory.success(loginUrl);
    }

    @GetMapping("/kakao/signupurl")
    public ResponseEntity<SuccessResponse<String>> getKakaoSignUpUrl() {
        String signUpUrl = String.format("https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                kakaoRestApiKey, kakaoSignUpRedirectionUrl);
        return ResponseFactory.success(signUpUrl);
    }

    @GetMapping("/kakao/login")
    public ResponseEntity<SuccessResponse<LoginServiceResponse>> login(@RequestParam("code") String code) {
        LoginServiceResponse response = loginService.login(code);
        return ResponseFactory.success(response);
    }

    @GetMapping("/kakao/signup")
    public ResponseEntity<SuccessResponse<SignUpServiceResponse>> signup(@RequestParam("code") String code) {
        SignUpServiceResponse response = signUpService.signUp(code);
        return ResponseFactory.success(response);
    }

    @PostMapping("/user/extrainfo")
    public ResponseEntity<SuccessResponse<AddDataForPendingUserServiceResponse>> addDataForPendingUser(@RequestBody @Valid AddDataForPendingUserRequest request, HttpServletRequest servletRequest) {
        Long userId = ((JwtToken) servletRequest.getAttribute("accessToken")).getUserId();
        AddDataForPendingUserServiceResponse response = addDataForPendingUserService.addDataForPendingUser(userId, request.toServiceRequest());
        return ResponseFactory.success(response);
    }
}
