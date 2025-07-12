package com.minhhai.ecommercebe.configuration.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiErrorResponse;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPatternParser;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class UrlFilter extends OncePerRequestFilter {
    private final RequestMappingHandlerMapping handlerMapping;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("---------- do filter url ----------");

        PathPatternParser parser = new PathPatternParser();

        boolean matched = handlerMapping.getHandlerMethods().keySet().stream()
                .flatMap(info -> info.getPatternValues().stream())
                .map(parser::parse)
                .anyMatch(pattern ->
                        pattern.matches(PathContainer.parsePath(request.getRequestURI())));

        if (!matched) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=UTF-8");

            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .status(ErrorCode.RESOURCE_NOT_FOUND.getCode())
                    .message(ErrorCode.RESOURCE_NOT_FOUND.getMessage())
                    .error(ErrorCode.RESOURCE_NOT_FOUND.name())
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            response.flushBuffer();
            return;
        }

        filterChain.doFilter(request, response);
    }
}
