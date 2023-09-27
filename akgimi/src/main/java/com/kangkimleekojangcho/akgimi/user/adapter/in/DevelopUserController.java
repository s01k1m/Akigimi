package com.kangkimleekojangcho.akgimi.user.adapter.in;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.user.application.DevelopLoginService;
import com.kangkimleekojangcho.akgimi.user.application.DevelopSignUpService;
import com.kangkimleekojangcho.akgimi.user.application.response.DevelopLoginServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.response.DevelopSignUpServiceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DevelopUserController {
    private final DevelopLoginService developLoginService;
    private final DevelopSignUpService developSignUpService;

    @GetMapping("/develop/login")
    public ResponseEntity<SuccessResponse<DevelopLoginServiceResponse>> developLogin(@RequestParam("user-id") long userId) {
        if(userId<0){
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "user id 값은 음수이면 안 됩니다.");
        }
        DevelopLoginServiceResponse response = developLoginService.login(userId);
        return ResponseFactory.success(response);
    }
}
