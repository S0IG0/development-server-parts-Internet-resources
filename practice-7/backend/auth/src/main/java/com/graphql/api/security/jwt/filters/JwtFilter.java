package com.graphql.api.security.jwt.filters;

import com.graphql.api.security.jwt.models.JwtModel;
import com.graphql.api.security.jwt.services.JwtModelService;
import com.graphql.api.security.jwt.services.TokenService;
import com.graphql.api.security.jwt.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final String Authorization = "Authorization";
    private final String type;
    private final JwtUtils jwtUtils;
    private final JwtModelService jwtModelService;
    private final TokenService tokenService;

    @Autowired
    public JwtFilter(
            @Value("${jwt.type}") String type,
            JwtUtils jwtUtils,
            JwtModelService jwtModelService,
            TokenService tokenService
    ) {
        this.type = type;
        this.jwtUtils = jwtUtils;
        this.jwtModelService = jwtModelService;
        this.tokenService = tokenService;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String header = request.getHeader(Authorization);
        if (StringUtils.hasText(header) && header.startsWith(type + " ")) {
            return header.substring(type.length() + 1);
        }
        return null;
    }


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest servletRequest,
            @NonNull HttpServletResponse servletResponse,
            @NonNull FilterChain filterChain
    ) throws IOException, ServletException {
        final String token = getTokenFromRequest(servletRequest);
        if (token != null && jwtUtils.validateAccessToken(token)) {
            final JwtModel jwtModel = jwtModelService.findByAccessToken(tokenService.findByValue(token));
            jwtModel.setAuthenticated(true);
            jwtModelService.saveJwtModel(jwtModel);
            SecurityContextHolder.getContext().setAuthentication(jwtModel);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
