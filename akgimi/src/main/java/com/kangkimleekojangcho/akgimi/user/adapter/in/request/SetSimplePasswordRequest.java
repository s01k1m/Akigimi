package com.kangkimleekojangcho.akgimi.user.adapter.in.request;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.kangkimleekojangcho.akgimi.user.application.request.SetSimplePasswordServiceRequest;
import lombok.Getter;

@Getter
public class SetSimplePasswordRequest {
    private String password;
}
