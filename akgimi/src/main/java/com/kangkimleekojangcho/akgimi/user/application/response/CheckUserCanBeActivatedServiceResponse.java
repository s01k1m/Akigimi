package com.kangkimleekojangcho.akgimi.user.application.response;

import com.kangkimleekojangcho.akgimi.user.domain.ActivateUserFailureCause;
import com.kangkimleekojangcho.akgimi.user.domain.UserField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CheckUserCanBeActivatedServiceResponse {
    private final boolean isAvailable;
    private final ActivateUserFailureCause cause;
    private final List<UserField> unfilledFields;

    public CheckUserCanBeActivatedServiceResponse(boolean isAvailable, ActivateUserFailureCause cause, List<UserField> unfilledFields) {
        this.isAvailable = isAvailable;
        this.cause = cause;
        this.unfilledFields = unfilledFields;
    }
}
