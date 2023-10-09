package com.kangkimleekojangcho.akgimi.user.application.port;


public interface CommandBlackListPort {

    String add(String tokenString,long time);
}
