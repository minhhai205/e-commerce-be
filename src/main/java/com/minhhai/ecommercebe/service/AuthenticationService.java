package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.configuration.securityModel.SecurityUser;
import com.minhhai.ecommercebe.dto.request.LoginRequestDTO;
import com.minhhai.ecommercebe.dto.response.TokenResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.model.Token;
import com.minhhai.ecommercebe.model.User;
import com.minhhai.ecommercebe.repository.UserRepository;
import com.minhhai.ecommercebe.util.commons.AppConst;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import com.minhhai.ecommercebe.util.enums.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    public TokenResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
        log.info("---------- authenticate login ----------");

        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // create new access token
        String accessToken = jwtService.generateToken(new SecurityUser(user), TokenType.ACCESS_TOKEN);

        // create new refresh token
        String refreshToken = jwtService.generateToken(new SecurityUser(user), TokenType.REFRESH_TOKEN);

        // save refresh token to DB
        tokenService.save(Token.builder()
                .jti(jwtService.extractJti(refreshToken, TokenType.REFRESH_TOKEN))
                .build());

        return TokenResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }

    public TokenResponseDTO refresh(HttpServletRequest request) {
        log.info("---------- refresh token ----------");

        final String refreshToken = request.getHeader("x-token");

        jwtService.validateToken(refreshToken, TokenType.REFRESH_TOKEN);

        String username = jwtService.extractUsername(refreshToken, TokenType.REFRESH_TOKEN);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String newAccessToken = jwtService.generateToken(new SecurityUser(user), TokenType.ACCESS_TOKEN);
        String newRefreshToken = jwtService.generateToken(new SecurityUser(user), TokenType.REFRESH_TOKEN);

        // delete old refresh token from DB
        tokenService.deleteByJti(jwtService.extractJti(refreshToken, TokenType.REFRESH_TOKEN));

        // save new refresh token to DB
        tokenService.save(Token.builder()
                .jti(jwtService.extractJti(newRefreshToken, TokenType.REFRESH_TOKEN))
                .build());

        return TokenResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .userId(user.getId())
                .build();
    }

    public String logout(HttpServletRequest request) {
        String accessToken = request.getHeader("a-token");
        String refreshToken = request.getHeader("b-token");

        // validate token
        jwtService.validateToken(accessToken, TokenType.ACCESS_TOKEN);
        jwtService.validateToken(refreshToken, TokenType.REFRESH_TOKEN);

        // add access token to black-list
        saveAccessTokenToBlackList(accessToken);

        // delete refresh token from DB
        tokenService.deleteByJti(jwtService.extractJti(refreshToken, TokenType.REFRESH_TOKEN));

        return "Logout successful!";
    }

    private void saveAccessTokenToBlackList(String accessToken) {
        String jtiAccessToken = jwtService.extractJti(accessToken, TokenType.ACCESS_TOKEN);
        Date expirationDate = jwtService.extractExpiration(accessToken, TokenType.ACCESS_TOKEN);
        long timeout = (expirationDate.getTime() - new Date().getTime()) / 1000;
        redisService.save(AppConst.TOKEN_PREFIX + jtiAccessToken, "black_list", timeout);
    }
}
