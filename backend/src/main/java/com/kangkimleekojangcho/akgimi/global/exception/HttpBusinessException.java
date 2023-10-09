package com.kangkimleekojangcho.akgimi.global.exception;

public abstract class HttpBusinessException extends BusinessException {

    public abstract int getStatusCode();
}
