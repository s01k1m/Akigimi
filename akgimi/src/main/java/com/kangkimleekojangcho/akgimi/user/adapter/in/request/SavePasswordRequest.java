package com.kangkimleekojangcho.akgimi.user.adapter.in.request;

import com.kangkimleekojangcho.akgimi.user.application.SavePasswordService;
import com.kangkimleekojangcho.akgimi.user.application.request.SavePasswordServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class SavePasswordRequest {
    @NotBlank
    private String password;
    @Positive
    private Long accountId;

    public SavePasswordServiceRequest toServiceRequest() {
        return new SavePasswordServiceRequest(accountId, password);
    }
}
