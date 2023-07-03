package com.vigacat.security.web.config.filter;

import com.vigacat.security.persistence.dto.TokenDto;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.service.component.UserService;
import com.vigacat.security.service.component.security.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            final Authentication authentication = buildAuthentication(tokenDto, applicationId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Authentication buildAuthentication(TokenDto tokenDto, Long appId) {
        final UserDto userDto = userService.getUser(tokenDto.getUsername(), appId);
        return new UsernamePasswordAuthenticationToken(userDto.getName(), tokenDto.getToken(), createAuthorities(userDto));
    }

    private Set<GrantedAuthority> createAuthorities(UserDto userDto) {
        return userDto.getRoles().stream()
                .flatMap(roleDto ->
                    Stream.concat(
                        Stream.of("role::".concat(roleDto.getName())),
                        roleDto.getPermissions().stream()
                            .map(permissionDto ->
                                "permission::".concat(permissionDto.getPermission())
                                )
                    )
                )
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
