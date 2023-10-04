package com.kangkimleekojangcho.akgimi.global.config;

import com.kangkimleekojangcho.akgimi.global.filter.JwtAuthenticationFilter;
import com.kangkimleekojangcho.akgimi.global.filter.JwtExceptionHandlerFilter;
import com.kangkimleekojangcho.akgimi.jwt.application.ConvertToJwtTokenService;
import com.kangkimleekojangcho.akgimi.jwt.application.ExtractTokenStringService;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryBlackListPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;


@Configuration
@EnableWebSecurity // 현재 클래스를 스프링 필터체인에 등록
@Log4j2
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final ExtractTokenStringService extractTokenStringService;
    private final ConvertToJwtTokenService convertToJwtTokenService;
    private final QueryBlackListPort queryBlackListPort;

    @Bean
    public MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    public JwtExceptionHandlerFilter jwtExceptionHandlerFilter() {
        return new JwtExceptionHandlerFilter();
    }

    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(extractTokenStringService, convertToJwtTokenService,queryBlackListPort);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtExceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // cross-origin 요청이 허가되는 url
        configuration.setAllowedOriginPatterns(List.of("*"));
        //허용할 헤더 설정
        configuration.addAllowedHeader("*");
        //허용할 http method
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
