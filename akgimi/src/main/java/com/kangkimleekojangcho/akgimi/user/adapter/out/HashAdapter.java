package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorException;
import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.HashPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class HashAdapter implements HashPort {

    @Override
    public String hash(String value, String salt){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(value.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new ServerErrorException(ServerErrorExceptionCode.CANNOT_HASH);
        }
    }
}
