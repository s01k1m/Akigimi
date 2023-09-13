package com.kangkimleekojangcho.akgimi.user.adapter.in;

import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.user.application.DevelopLoginService;
import com.kangkimleekojangcho.akgimi.user.application.DevelopSignUpService;
import com.kangkimleekojangcho.akgimi.user.application.response.DevelopLoginServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.response.DevelopSignUpServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.response.LoginServiceResponse;
import com.sun.net.httpserver.Authenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DevelopUserController {
    private final DevelopLoginService developLoginService;

    @GetMapping("/develop/login")
    public ResponseEntity<SuccessResponse<DevelopLoginServiceResponse>> developLogin(@RequestParam("user-id") long userId) {
        DevelopLoginServiceResponse response = developLoginService.login(userId);
        return ResponseFactory.success(response);
    }
}
