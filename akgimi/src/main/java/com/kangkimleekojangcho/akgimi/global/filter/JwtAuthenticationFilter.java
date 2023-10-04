package com.kangkimleekojangcho.akgimi.global.filter;


import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import com.kangkimleekojangcho.akgimi.jwt.application.ConvertToJwtTokenService;
import com.kangkimleekojangcho.akgimi.jwt.application.ExtractTokenStringService;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryBlackListPort;
import com.kangkimleekojangcho.akgimi.user.domain.AccessToken;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION = "Authorization";
    private final ExtractTokenStringService extractTokenStringService;
    private final ConvertToJwtTokenService convertToJwtTokenService;
    private final QueryBlackListPort queryBlackListPort;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getServletPath();
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String rawToken = extractTokenStringService.extract(authorizationHeader);
        AccessToken accessToken = convertToJwtTokenService.convertToAccessToken(rawToken);
        saveAccessTokenToHttpRequest(request, accessToken);
        checkTokenIsNotExpired(accessToken);
        checkTokenIsNotLogouttedToken(accessToken);
        checkUserCanAccessWithoutCheckingSimplePassword(requestUri,accessToken);
        checkPendingUserCanAccess(requestUri, accessToken);
        log.info("ID가 '{}'인 유저가 요청 메시지를 보냅니다.", accessToken.getUserId());
        filterChain.doFilter(request, response);
    }

    private void checkUserCanAccessWithoutCheckingSimplePassword(String requestUri, AccessToken accessToken) {
        HashSet<String> set = new HashSet<>(
                List.of("/user/accesstoken/reissue",
                        "/user/nickname/duplicate",
                        "/user/nickname/recommend",
                        "/user/nickname",
                        "/account/new",
                        "/account/new/password",
                        "/user/password/simple",
                        "/user/password/simple/check",
                        "/user/profile",
                        "/user/activate",
                        "/user/can-activate"));
        if(!accessToken.isSimplePasswordChecked() && !set.contains(requestUri)){
            throw new UnauthorizedException(UnauthorizedExceptionCode.SIMPLE_PASSWORD_IS_NOT_CHECKED);
        }
    }

    private void saveAccessTokenToHttpRequest(HttpServletRequest request, AccessToken accessToken) {
        request.setAttribute("accessToken", accessToken);
    }

    private void checkPendingUserCanAccess(String requestUriValue, AccessToken accessToken) {
        if(UserState.PENDING.equals(accessToken.getUserState()) &&
        !canAccessEvenThoughUserStateIsPending(requestUriValue)){
            throw new UnauthorizedException(UnauthorizedExceptionCode.NOT_ENOUGH_INFO);
        }
    }

    private void checkTokenIsNotLogouttedToken(AccessToken accessToken) {
        if (queryBlackListPort.find(accessToken.getRawToken())) {
            throw new UnauthorizedException(UnauthorizedExceptionCode.LOGOUT_TOKEN);
        }
    }

    private static void checkTokenIsNotExpired(AccessToken accessToken) {
        if (accessToken.isExpired(new Date())) {
            throw new UnauthorizedException(UnauthorizedExceptionCode.TOKEN_EXPIRED);
        }
    }

    private boolean canAccessEvenThoughUserStateIsPending(String path) {
        HashSet<String> set = new HashSet<>(
                List.of("/user/accesstoken/reissue",
                "/user/nickname/duplicate",
                "/user/nickname/recommend",
                "/user/nickname",
                "/account/new",
                "/account/new/password",
                "/user/password/simple",
                "/user/password/simple/check",
                "/user/profile",
                "/user/activate",
                "/user/can-activate"));
        return set.contains(path);
    }

    private boolean canAccessEvenThoughNotSimplePasswordChecked(String path){
        Set<String> set = new HashSet<>(List.of("/user/password/simple",
                "user/password/simple/check"));
        return set.contains(path);
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
                new AntPathRequestMatcher("/develop/login", HttpMethod.GET.name()),
                new AntPathRequestMatcher("/user/nickname/duplicate", HttpMethod.GET.name()),
                new AntPathRequestMatcher("/user/reissue", HttpMethod.POST.name()),
                new AntPathRequestMatcher("/swagger-ui/*",HttpMethod.GET.name()),
                new AntPathRequestMatcher("/v3/api-docs"),
                new AntPathRequestMatcher("/v3/api-docs/*",HttpMethod.GET.name()),
                new AntPathRequestMatcher("/develop/signup",HttpMethod.POST.name())
        );
        OrRequestMatcher skipList = new OrRequestMatcher(permitList);
        return skipList.matches(request);
    }
}
