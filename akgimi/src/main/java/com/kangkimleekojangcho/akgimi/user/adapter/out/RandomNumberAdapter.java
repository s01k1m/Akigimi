package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.application.port.RandomNumberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
class RandomNumberAdapter implements RandomNumberPort {

    @Override
    public int generate(int from, int to) {
        double randomValue = Math.random();
        int min = from;
        int max = to-1;
        int range = max - min + 1;
        return (int) (randomValue * range) + min;
    }

    @Override
    public String generateDigit(int length) {
        // 가능한 숫자 문자
        String numbers = "0123456789";

        // 랜덤한 문자열을 생성하기 위한 StringBuilder 초기화
        StringBuilder sb = new StringBuilder(length);

        // 랜덤 숫자를 생성하여 StringBuilder에 추가
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(numbers.length());
            char randomChar = numbers.charAt(randomIndex);
            sb.append(randomChar);
        }

        // 생성된 랜덤 문자열 반환
        return sb.toString();
    }
}
