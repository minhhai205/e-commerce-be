package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.model.Token;
import com.minhhai.ecommercebe.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void save(Token token) {
        tokenRepository.save(token);
    }

    public void deleteByJti(String jti) {
        tokenRepository.deleteById(jti);
    }

    public Optional<Token> findByJti(String jti) {
        return tokenRepository.findById(jti);
    }
}
