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

    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    public void delete(Token token) {
        tokenRepository.delete(token);
    }

    public Optional<Token> findByJti(String jti) {
        return tokenRepository.findById(jti);
    }
}
