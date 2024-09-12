package com.vigacat.security.web.config.filter;

import com.vigacat.security.persistence.dto.TokenDto;
import com.vigacat.security.service.component.AppService;
import com.vigacat.security.service.component.security.AuthenticationService;
import com.vigacat.security.service.component.security.TokenService;
import com.vigacat.security.web.constant.HeaderConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TokenRequestFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthenticationService authenticationService;
    private final AppService appService;
    @Value("${vigacat.app.name}")
    private String applicationName;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HeaderConstant.AUTHORIZATION_NAME);
        if (Objects.nonNull(authorizationHeader)) {
            final TokenDto tokenDto = tokenService.getValidToken(authorizationHeader);
            final Long applicationId = appService.getAppIdByName(applicationName);
            final Authentication authentication = authenticationService.buildAuthentication(tokenDto, applicationId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
