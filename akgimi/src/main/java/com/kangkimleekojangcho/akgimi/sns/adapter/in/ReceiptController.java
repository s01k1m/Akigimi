package com.kangkimleekojangcho.akgimi.sns.adapter.in;

import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.GetBunchOfReceiptRequest;
import com.kangkimleekojangcho.akgimi.sns.application.GetBunchOfReceiptService;
import com.kangkimleekojangcho.akgimi.sns.application.response.GetBunchOfReceiptServiceResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReceiptController {

    private final GetBunchOfReceiptService getBunchOfReceiptService;
    private final SubtractUserIdFromAccessTokenService userIdFromAccessTokenService;

    @GetMapping("/receipt/{userId}")
    ResponseEntity<SuccessResponse<GetBunchOfReceiptServiceResponse>> getBunchOfReceipt(
            @Valid GetBunchOfReceiptRequest getBunchOfReceiptRequest,
            @PathVariable Long userId,
            HttpServletRequest servletRequest
    ) {
//        Long userId = userIdFromAccessTokenService.subtract(servletRequest);
        return ResponseFactory.success(getBunchOfReceiptService.getBunchOfReceipt(
                userId, getBunchOfReceiptRequest.toServiceRequest()));
    }
}
