package com.minhhai.ecommercebe.configuration.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minhhai.ecommercebe.configuration.securityModel.PublicUrl;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiErrorResponse;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import jakarta.annotation.PostConstruct;
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
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class UrlFilter extends OncePerRequestFilter {

    private final RequestMappingHandlerMapping handlerMapping;

    private final Set<PathPattern> ignoredPathPatterns = new HashSet<>();
    private final Set<PathPattern> handlerPathPatterns = new HashSet<>();
    private final PathPatternParser parser = new PathPatternParser();

    @PostConstruct
    public void init() {
        PublicUrl.IGNORED_PATTERNS.forEach(p -> ignoredPathPatterns.add(parser.parse(p)));
        handlerMapping.getHandlerMethods().keySet().forEach(info ->
                info.getPatternValues().forEach(pattern -> handlerPathPatterns.add(parser.parse(pattern)))
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        PathContainer requestPath = PathContainer.parsePath(requestUri);

        log.info("---------- do filter url: {} ----------", requestUri);

        if (ignoredPathPatterns.stream().anyMatch(pattern -> pattern.matches(requestPath))) {
            filterChain.doFilter(request, response);
            return;
        }

        if (handlerPathPatterns.stream().noneMatch(pattern -> pattern.matches(requestPath))) {
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
