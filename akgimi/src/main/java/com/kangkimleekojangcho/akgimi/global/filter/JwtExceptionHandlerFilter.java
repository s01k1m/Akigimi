package com.kangkimleekojangcho.akgimi.global.filter;

import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            String servletPath = request.getRequestURI();
            log.info("======= >> URI = {} =======", servletPath);
            log.info("query string = {}", request.getQueryString());
            printUnAuthorizedExceptionInformation(e);
            ResponseFactory.fail(response, e);
        }
    }

    private void printUnAuthorizedExceptionInformation(UnauthorizedException e) {
        log.info("======= << Unauthorized! =======");
        log.info("cause: {}", e.getCode());
        log.info("message: {}", e.getMessage());
    }
}

