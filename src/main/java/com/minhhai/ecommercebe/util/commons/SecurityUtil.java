package com.minhhai.ecommercebe.util.commons;

import com.minhhai.ecommercebe.configuration.securityModel.SecurityUser;
import com.minhhai.ecommercebe.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class SecurityUtil {
    public static User getCurrentUser() {
        SecurityUser currentSecurityUser = (SecurityUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return currentSecurityUser.getUser();
    }

    public static List<String> getCurrentUserAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
