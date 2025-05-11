package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.configuration.securityModel.SecurityUser;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.exception.JwtException;
import com.minhhai.ecommercebe.model.Token;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import com.minhhai.ecommercebe.util.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    @Value("${security.jwt.access.expired}")
    private long accessExpired;

    @Value("${security.jwt.refresh.expired}")
    private long refreshExpired;

    @Value("${security.jwt.access.key}")
    private String accessKey;

    @Value("${security.jwt.refresh.key}")
    private String refreshKey;

    private final TokenService tokenService;

    public String generateToken(SecurityUser user, TokenType tokenType) {
        return generateToken(new HashMap<>(), user, tokenType);
    }

    public void validateToken(String token, TokenType tokenType) {
        try {
            extraAllClaim(token, tokenType);
        } catch (Exception e) {
            if (tokenType.equals(TokenType.ACCESS_TOKEN)) {
                throw new JwtException(ErrorCode.ACCESS_TOKEN_INVALID);
            } else if (tokenType.equals(TokenType.REFRESH_TOKEN)) {
                throw new JwtException(ErrorCode.REFRESH_TOKEN_INVALID);
            }
        }
        
        checkToken(token, tokenType);
    }

    public void checkToken(String token, TokenType tokenType) {
        if (tokenType.equals(TokenType.ACCESS_TOKEN)) {
            // check access token in black-list
            return;
        } else if (tokenType.equals(TokenType.REFRESH_TOKEN)) {
            tokenService.findByJti(extractJti(token, tokenType))
                    .orElseThrow(() -> new JwtException(ErrorCode.REFRESH_TOKEN_INVALID));
        }
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails, TokenType tokenType) {
        log.info("------------------------- generate token ------------------------------");

        long expiredTime = 1L;
        if (tokenType.equals(TokenType.ACCESS_TOKEN)) {
            expiredTime = 1000 * 60 * 60 * accessExpired;
        } else if (tokenType.equals(TokenType.REFRESH_TOKEN)) {
            expiredTime = 1000 * 60 * 60 * 24 * refreshExpired;
        }

        return Jwts.builder()
                .setClaims(claims) // Đặt thông tin bổ sung vào token
                .setId(UUID.randomUUID().toString())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(getKey(tokenType), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key getKey(TokenType type) {
        switch (type) {
            case ACCESS_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
            }
            case REFRESH_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
            }
            default -> throw new AppException(ErrorCode.TOKEN_TYPE_INVALID);
        }

    }

    public String extractUsername(String token, TokenType tokenType) {
        return extractClaim(token, tokenType, Claims::getSubject);
    }

    public String extractJti(String token, TokenType tokenType) {
        return extractClaim(token, tokenType, Claims::getId);
    }

    private <T> T extractClaim(String token, TokenType type, Function<Claims, T> claimResolver) {
        final Claims claims = extraAllClaim(token, type);
        return claimResolver.apply(claims);
    }

    private Claims extraAllClaim(String token, TokenType type) {
        return Jwts.parserBuilder().setSigningKey(getKey(type)).build().parseClaimsJws(token).getBody();
    }
}
