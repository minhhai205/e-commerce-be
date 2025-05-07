package com.minhhai.ecommercebe.configuration.securityModel;

import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.model.Permission;
import com.minhhai.ecommercebe.model.Role;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityAuthority<T> implements GrantedAuthority {

    private final T authority;

    @Override
    public String getAuthority() {
        if (authority instanceof Role) {
            return "ROLE_" + ((Role) authority).getName();
        } else if (authority instanceof Permission) {
            return ((Permission) authority).getName();
        } else {
            throw new AppException(ErrorCode.AUTHORITY_NOT_SUPPORTED);
        }
    }
}
