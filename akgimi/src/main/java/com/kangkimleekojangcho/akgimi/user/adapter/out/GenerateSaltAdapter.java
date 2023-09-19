package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.user.application.port.GenerateSaltPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class GenerateSaltAdapter implements GenerateSaltPort {
    @Override
    public String generate() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
}
