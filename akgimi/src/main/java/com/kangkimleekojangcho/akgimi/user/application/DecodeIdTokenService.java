package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorException;
import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorExceptionCode;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import com.kangkimleekojangcho.akgimi.user.adapter.out.GetKakaoPublicKeyFromMyselfAdapter;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoIdToken;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoPublicKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;

import static com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode.INVALID_TOKEN;
import static com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode.TOKEN_EXPIRED;

@Service
@RequiredArgsConstructor
public class DecodeIdTokenService {
    @Value("${kakao.iss}")
    private String iss;
    @Value("${kakao.rest-api-key}")
    private String restApiKey;
    private final GetKakaoPublicKeyFromMyselfAdapter getKakaoPublicKeyFromMyselfAdapter;
    private final KakaoPublicKeyGenerator kakaoPublicKeyGenerator;

    public KakaoIdToken decode(String rawIdToken) {
        String rawPublicKey = getKakaoPublicKeyFromMyselfAdapter.get();
        List<KakaoPublicKey> kakaoPublicKeyList = kakaoPublicKeyGenerator.generate(rawPublicKey);
        String kid = getKid(rawIdToken);
        KakaoPublicKey targetPublicKey = findTargetPublicKey(kakaoPublicKeyList, kid);
        Claims body = getOidcTokenJws(rawIdToken, targetPublicKey.getN(), targetPublicKey.getE()).getBody();
        return new KakaoIdToken(
                body.getIssuer(),
                body.getAudience(),
                body.getSubject(),
                body.get("email", String.class),
                body.get("nickname", String.class),
                body.get("picture", String.class));
    }

    private KakaoPublicKey findTargetPublicKey(List<KakaoPublicKey> kakaoPublicKeyList, String kid) {
        return kakaoPublicKeyList.stream()
                .filter(o -> o.getKid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new ServerErrorException(ServerErrorExceptionCode.NO_OIDC_PUBLIC_KEY));
    }

    private String getKid(String idToken) {
        try {
            return (String) Jwts.parserBuilder()
                    .requireAudience(restApiKey)
                    .requireIssuer(iss)
                    .build()
                    .parseClaimsJwt(getUnsignedToken(idToken))
                    .getHeader().get("kid");
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(TOKEN_EXPIRED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(ServerErrorExceptionCode.NETWORK_ERROR);
        }
    }
    private Jws<Claims> getOidcTokenJws(String token, String modulus, String exponent) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getRsaPublicKey(modulus, exponent))
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new UnauthorizedException(INVALID_TOKEN);
        }
    }

    private Key getRsaPublicKey(String modulus, String exponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodeN = Base64.getUrlDecoder().decode(modulus);
        byte[] decodeE = Base64.getUrlDecoder().decode(exponent);
        BigInteger n = new BigInteger(1, decodeN);
        BigInteger e = new BigInteger(1, decodeE);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
        return keyFactory.generatePublic(keySpec);
    }

    private String getUnsignedToken(String token) throws Exception {
        String[] splitToken = token.split("\\.");
        if (splitToken.length != 3) throw new Exception();
        return splitToken[0] + "." + splitToken[1] + "."; // 헤더, 페이로드 반환
    }
}
