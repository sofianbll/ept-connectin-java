package com.connect_in.backend.shared.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Normalizes double slashes in request URIs.
 * Workaround for PostItem.jsx which builds URLs like /storage//storage/avatars/...
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DoubleSlashFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        if (uri.contains("//")) {
            String cleaned = uri.replaceAll("/+", "/");
            HttpServletRequest wrapped = new HttpServletRequestWrapper(request) {
                @Override
                public String getRequestURI() {
                    return cleaned;
                }

                @Override
                public StringBuffer getRequestURL() {
                    return new StringBuffer(request.getScheme() + "://" +
                            request.getServerName() + ":" +
                            request.getServerPort() + cleaned);
                }

                @Override
                public String getServletPath() {
                    return cleaned;
                }
            };
            filterChain.doFilter(wrapped, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
