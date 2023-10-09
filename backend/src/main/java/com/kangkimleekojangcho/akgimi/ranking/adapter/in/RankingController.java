package com.kangkimleekojangcho.akgimi.ranking.adapter.in;

import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.ranking.application.GetAllRankingService;
import com.kangkimleekojangcho.akgimi.ranking.application.response.GetAllRankingServiceResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;
    private final GetAllRankingService getAllRankingService;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<GetAllRankingServiceResponse>>> getAllRanking(
            HttpServletRequest servletRequest
    ){
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        return ResponseFactory.success(getAllRankingService.get(userId));
    }
}
