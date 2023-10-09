package com.kangkimleekojangcho.akgimi.user.application.port;

public interface QueryBlackListPort {
    boolean find(String tokenString);
}
