package com.vigacat.security.filterRequestVigacat.component;

import com.vigacat.security.filterRequestVigacat.component.service.FilterAuthenticationService;
import com.vigacat.security.filterRequestVigacat.component.service.FilterTokenService;
import com.vigacat.security.filterRequestVigacat.dto.TokenDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenRequestFilter extends OncePerRequestFilter {

    private static final String LOG_PREFIX = "Token Request Filter >> ";
    private static final String HEADER_AUTHORIZATION_NAME = "Authorization";
    private static final String HEADER_APPLICATION_NAME = "app_id";

    private final FilterTokenService filterTokenService;
    private final FilterAuthenticationService filterAuthenticationService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION_NAME);
        if (Objects.nonNull(authorizationHeader)) {
            log.info("{} Do filter internal", LOG_PREFIX);
            final TokenDto tokenDto = filterTokenService.getValidToken(authorizationHeader);
            final Long applicationId = Long.valueOf(request.getHeader(HEADER_APPLICATION_NAME));
            final Authentication authentication = filterAuthenticationService.buildAuthentication(tokenDto, applicationId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
