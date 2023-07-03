package com.vigacat.security.web.config.filter;

import com.vigacat.security.persistence.dto.TokenDto;
import com.vigacat.security.service.component.UserService;
import com.vigacat.security.service.component.security.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TokenRequestFilter extends OncePerRequestFilter {
    private static final String HEADER_AUTHORIZATION_NAME = "Authorization";
    private static final String HEADER_APPLICATION_NAME = "app_id";

    private final TokenService tokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION_NAME);

        if(Objects.nonNull(authorizationHeader)) {
            final TokenDto tokenDto = tokenService.getValidToken(authorizationHeader);
            final Long applicationId = Long.valueOf(request.getHeader(HEADER_APPLICATION_NAME));
            final Authentication authentication = userService.buildAuthentication(tokenDto, applicationId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
