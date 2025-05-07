package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.configuration.securityModel.SecurityUser;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.repository.UserRepository;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var u = userRepository.findByUsername(username);

        return u.map(SecurityUser::new)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
