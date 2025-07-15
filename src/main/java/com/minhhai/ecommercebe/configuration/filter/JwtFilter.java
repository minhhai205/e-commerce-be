package com.minhhai.ecommercebe.configuration.filter;


import com.minhhai.ecommercebe.configuration.securityCustom.CustomAuthEntryPoint;
import com.minhhai.ecommercebe.configuration.securityModel.PublicUrl;
import com.minhhai.ecommercebe.exception.JwtException;
import com.minhhai.ecommercebe.service.JpaUserDetailsService;
import com.minhhai.ecommercebe.service.JwtService;
import com.minhhai.ecommercebe.util.enums.TokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final JpaUserDetailsService userDetailsService;
    private final CustomAuthEntryPoint authEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("---------- do filter jwt ----------");

        final String authorization = request.getHeader(AUTHORIZATION);

        if (StringUtils.isBlank(authorization)
                || isPublicUrl(request.getRequestURI())
                || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authorization.substring("Bearer ".length());

        try {
            // validate token
            jwtService.validateToken(token, TokenType.ACCESS_TOKEN);

            // set authentication
            final String userName = jwtService.extractUsername(token, TokenType.ACCESS_TOKEN);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            log.info("---------- jwt exception ------------");
            authEntryPoint.commence(request, response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicUrl(String path) {
        return PublicUrl.WHITE_LIST.stream().anyMatch(publicUrl ->
                publicUrl.endsWith("/**")
                        ? path.startsWith(publicUrl.replace("/**", ""))
                        : path.equals(publicUrl)
        );
    }
}