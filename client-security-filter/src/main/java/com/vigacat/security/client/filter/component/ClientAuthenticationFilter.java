package com.vigacat.security.client.filter.component;

import com.vigacat.security.client.filter.component.service.AuthenticationClientService;
import com.vigacat.security.client.filter.component.webclient.constant.ClientHeader;
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
public class ClientAuthenticationFilter extends OncePerRequestFilter {

    private static final String LOG_PREFIX = "Client Filter Request >> ";

    private final AuthenticationClientService authenticationClientService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(ClientHeader.AUTHORIZATION_NAME);
        if (Objects.nonNull(authorizationHeader)) {
            log.info("{} Do filter internal", LOG_PREFIX);
            final Authentication authentication = authenticationClientService.buildAuthentication(authorizationHeader);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
