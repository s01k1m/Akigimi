package com.kangkimleekojangcho.akgimi.user.application.port;

public interface RandomNumberPort {
    int generate(int from, int to);
    String generateDigit(int length);
}
