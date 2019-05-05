package com.suez.acoustic_logger.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suez.acoustic_logger.entity.User;
import com.suez.acoustic_logger.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    private static String SECRET_KEY = "I/ZgzcRMrX8jiauR7jAnaa9523JThM2Hch6KJyfbSGMsyEbkoXYUL8Z2TWDmv5W0y6MwoK0sXRTl8wIjzQj6s28QKeYg1xPUg4mYkAR/px289u4IguNIfLO8iES59fay+3HdMC1MhdX0n6Bue1HPYe6RmX+aJr2oOb8DrEeWoLy0I4QV0eJKWGKOUxtDUNqkNM0PCk13Wv5JqvowHENKoaNvligGc5ehLZ1nNbdpAn1Ib7kjAlR4kcdlE1N6OzJ/vN+j8eWSUbr2PcubHk4gfL3iyiWoVrkb4fopBkpGOAofz2EI78DhwNblrEcz3dqu8ozZVMCpw+Ifv6FdTzffww==";

    private final JwtConfig jwtConfig;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtConfig jwtConfig) {
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;

        // By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
        // In our case, we use "/auth". So, we need to override the defaults.
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/**", "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String str =  request.getHeader("authorization");
//        String[] splited = str.split("\\s+");
//        System.out.println(str);
//        if (splited[0].equals("Bearer"))
//            System.out.println(splited[1]);
//
//        return null;

        try {

            // 1. Get credentials from request
            User creds = new ObjectMapper().readValue(request.getInputStream(), User.class);
            System.out.println("read user: " + creds.toString());

            // 2. Create auth object (contains credentials) which will be used by auth manager
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    creds.getUsername(), creds.getPassword(), Collections.emptyList());

            // 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
            System.out.println("token: " + authToken.toString());

            return authManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("Failed" + failed.getMessage());
        System.out.println("Faile request: " + request.toString());

    }

    // Upon successful authentication, generate a token.
    // The 'auth' passed to successfulAuthentication() is the current authenticated user.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        //The JWT signature algorithm to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //Sign JWT with the ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(auth.getName())
                // Convert to list of strings.
                // This is important because it affects the way we get them back in the Gateway.
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
                .signWith(signingKey, signatureAlgorithm)
                .compact();

        System.out.println("generated token:" + token);

        // Add token to header
        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
    }

}
