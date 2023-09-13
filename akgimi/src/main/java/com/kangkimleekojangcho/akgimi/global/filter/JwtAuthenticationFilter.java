package com.kangkimleekojangcho.akgimi.global.filter;


import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import com.kangkimleekojangcho.akgimi.jwt.application.CreateJwtTokenService;
import com.kangkimleekojangcho.akgimi.jwt.application.ExtractTokenStringService;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryBlackListPort;
import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpMethod;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION = "Authorization";
    private final ExtractTokenStringService extractTokenStringService;
    private final CreateJwtTokenService createJwtTokenService;
    private final QueryBlackListPort queryBlackListPort;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUriValue = request.getServletPath();
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String tokenString = extractTokenStringService.extract(authorizationHeader);
        JwtToken accessToken = createJwtTokenService.create(tokenString);
        request.setAttribute("accessToken", accessToken);

        if (accessToken.isExpired(new Date())) {
            throw new UnauthorizedException(UnauthorizedExceptionCode.TOKEN_EXPIRED);
        }
        if (accessToken.isAccessToken() && queryBlackListPort.find(tokenString)) {
            throw new UnauthorizedException(UnauthorizedExceptionCode.LOGOUT_TOKEN);
        }
        shouldNotUseApplicationWhenUserHasRefreshToken(requestUriValue, accessToken);
        shouldNotReissueWhenUserHasRefreshToken(requestUriValue, accessToken);
        filterChain.doFilter(request, response);
    }

    // Access Token 재발급 요청 api 인데, access token을 전달했을 경우인지 체크
    private void shouldNotReissueWhenUserHasRefreshToken(String path, JwtToken jwtToken) {
        if (!doesUserRequestReassuranceOfAccessToken(path) && jwtToken.isRefreshToken()) {
            throw new UnauthorizedException(UnauthorizedExceptionCode.NOT_ACCESS_TOKEN);
        }
    }

    // 재발급 요청이 아닌데 refresh token을 전달했을 경우인지 체크
    private void shouldNotUseApplicationWhenUserHasRefreshToken(String path, JwtToken jwtToken) {
        if (doesUserRequestReassuranceOfAccessToken(path) && jwtToken.isAccessToken()) {
            throw new UnauthorizedException(UnauthorizedExceptionCode.NOT_REFRESH_TOKEN);
        }
    }

    private boolean doesUserRequestReassuranceOfAccessToken(String path) {
        return path.startsWith("/token");
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<RequestMatcher> permitList = List.of(
                new AntPathRequestMatcher("/kakao/login", HttpMethod.POST.name()),
                new AntPathRequestMatcher("/kakao/signup", HttpMethod.POST.name()),
                new AntPathRequestMatcher("/kakao/loginurl", HttpMethod.GET.name()),
                new AntPathRequestMatcher("/user/signup", HttpMethod.POST.name()),
                new AntPathRequestMatcher("/kakao/signupurl", HttpMethod.GET.name()),
                new AntPathRequestMatcher("/publickeys", HttpMethod.GET.name()),
                new AntPathRequestMatcher("/kakao/idtoken", HttpMethod.GET.name()),
                new AntPathRequestMatcher("/develop/login", HttpMethod.GET.name())
        );
        OrRequestMatcher skipList = new OrRequestMatcher(permitList);
        return skipList.matches(request);
    }
}
