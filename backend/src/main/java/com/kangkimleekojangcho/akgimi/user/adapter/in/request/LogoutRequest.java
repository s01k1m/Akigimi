package com.kangkimleekojangcho.akgimi.user.adapter.in.request;


import com.kangkimleekojangcho.akgimi.user.application.request.LogoutServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LogoutRequest {
    @NotBlank
    private String accessToken;
    @NotBlank
    private String refreshToken;

    public LogoutServiceRequest toServiceRequest() {
        return new LogoutServiceRequest(accessToken, refreshToken);
    }
}
