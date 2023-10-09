package com.kangkimleekojangcho.akgimi.user.adapter.in.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @NotBlank(message = "id token 값은 공백이어선 안 됩니다.")
    private String idToken;
}
