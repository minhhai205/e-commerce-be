package com.minhhai.ecommercebe.util.commons;

import com.minhhai.ecommercebe.configuration.securityModel.SecurityUser;
import com.minhhai.ecommercebe.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static User getCurrentUser() {
        SecurityUser currentSecurityUser = (SecurityUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return currentSecurityUser.getUser();
    }
}
