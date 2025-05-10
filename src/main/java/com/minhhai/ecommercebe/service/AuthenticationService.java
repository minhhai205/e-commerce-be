package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.configuration.securityModel.SecurityUser;
import com.minhhai.ecommercebe.dto.request.LoginRequestDTO;
import com.minhhai.ecommercebe.dto.response.TokenResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.model.Token;
import com.minhhai.ecommercebe.model.User;
import com.minhhai.ecommercebe.repository.UserRepository;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import com.minhhai.ecommercebe.util.enums.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public TokenResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
        log.info("---------- authenticate login ----------");

        User user = userService.findByUsername(loginRequestDTO.getUsername())
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
}
