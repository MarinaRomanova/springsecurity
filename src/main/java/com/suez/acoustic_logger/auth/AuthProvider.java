package com.suez.acoustic_logger.auth;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.suez.acoustic_logger.entity.User;
import io.jsonwebtoken.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class AuthProvider {

    //node -e "console.log(require('crypto').randomBytes(256).toString('base64'));"
    //@Value("${auth.provider.secret_key}")
    private static String SECRET_KEY = "I/ZgzcRMrX8jiauR7jAnaa9523JThM2Hch6KJyfbSGMsyEbkoXYUL8Z2TWDmv5W0y6MwoK0sXRTl8wIjzQj6s28QKeYg1xPUg4mYkAR/px289u4IguNIfLO8iES59fay+3HdMC1MhdX0n6Bue1HPYe6RmX+aJr2oOb8DrEeWoLy0I4QV0eJKWGKOUxtDUNqkNM0PCk13Wv5JqvowHENKoaNvligGc5ehLZ1nNbdpAn1Ib7kjAlR4kcdlE1N6OzJ/vN+j8eWSUbr2PcubHk4gfL3iyiWoVrkb4fopBkpGOAofz2EI78DhwNblrEcz3dqu8ozZVMCpw+Ifv6FdTzffww==";



    public static String createJWT(User user) {
        //The JWT signature algorithm to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //Sign JWT with the ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Long now = System.currentTimeMillis();
        HashMap<String, Object> role = new HashMap<>();
        role.put("role", user.getRole());
        //sets the JWT Claims
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setClaims(role)
                .setIssuedAt(new Date(now))
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date(now + 10*60*1000)).compact();
    }

    public static Claims decodeJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
