package com.pengyd.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @description: TODO - 
 * @author:
 * @createTime: 2018年3月9日 上午10:43:56
 *
 */
@Component("jwtToken")
public class JWTToken {
    private static final String SecretKey = "1l11ll11l1llll1";

    private static final long ttlMillis = 604800000L;

    public static String createJWT(String id, String issuer, String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("1l11ll11l1llll1");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        long expMillis = nowMillis + 604800000L;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        return builder.compact();
    }

    public static Claims parseJWT(String jwt) {
        Claims claims = (Claims) Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("1l11ll11l1llll1"))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static void main(String[] args) {
        Claims c = parseJWT("");
        c.getSubject();
    }
}
