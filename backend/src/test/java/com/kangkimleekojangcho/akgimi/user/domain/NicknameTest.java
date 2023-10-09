package com.kangkimleekojangcho.akgimi.user.domain;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NicknameTest {
    @Test
    @DisplayName("성공 케이스")
    void testor() {
        // given
        assertDoesNotThrow(() -> new Nickname("안녕하세요12345"));
        // when
    
        // then
    }
    @Test
    @DisplayName("두 개 이상 연속된 공백은 들어가면 안 된다")
    void test() {
        // given
        Assertions.assertThatThrownBy(() -> new Nickname("1234567  890")).isInstanceOf(BadRequestException.class)
                .hasMessage("닉네임에는 공백이 연속해서 두 개 이상 들어가선 안 됩니다.");
        // when

        // then
    }
    @Test
    @DisplayName("양 끝에 있는 공백은 제거한다.")
    void test3() {
        // given
        Nickname nickname = new Nickname("  1234567890  ");
        // when

        // then
        Assertions.assertThat(nickname.getValue()).isEqualTo("1234567890");

    }
    @Test
    @DisplayName("특수문자는 포함되면 안 된다")
    void test1() {
        // given
        Assertions.assertThatThrownBy(() -> new Nickname("안녕하세요.1234")).isInstanceOf(BadRequestException.class).hasMessage("닉네임에는 특수문자가 포함되면 안 됩니다.");
        // when

        // then
    }

}