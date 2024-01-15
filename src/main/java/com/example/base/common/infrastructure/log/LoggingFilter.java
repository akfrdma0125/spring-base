package com.example.base.common.infrastructure.log;

import com.example.base.common.infrastructure.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);

            if (!isHealthCheckRequest(wrappedRequest)) {
                logRequest(wrappedRequest);
                logResponse(wrappedResponse);
            }
        } finally {
            MDC.clear();
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) throws IOException {
        String contentType = request.getContentType();
        String queryString = request.getQueryString();
        String requestUri = queryString == null ? request.getRequestURI() : request.getRequestURI() + "?" + queryString;

        if (shouldLogContent(contentType)) {
            String content = new String(request.getContentAsByteArray(), request.getCharacterEncoding());
            log.info(Constant.REQUEST_BODY_MESSAGE_FORMAT, request.getMethod(), requestUri, contentType, content);
            return;
        }

        log.info(Constant.REQUEST_MESSAGE_FORMAT, request.getMethod(), requestUri);
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        if (response.getContentSize() > 0) {
            String content = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
            if (response.getStatus() >= 400) {
                log.error(Constant.RESPONSE_MESSAGE_FORMAT, content);
            } else {
                log.info(Constant.RESPONSE_MESSAGE_FORMAT, content);
            }
        }
    }

    private boolean shouldLogContent(String contentType) {
        return contentType != null && !contentType.startsWith("multipart/form-data");
    }


    private boolean isHealthCheckRequest(HttpServletRequest request) {
        return "/health-check".equals(request.getRequestURI()) && "GET".equalsIgnoreCase(request.getMethod());
    }
}
