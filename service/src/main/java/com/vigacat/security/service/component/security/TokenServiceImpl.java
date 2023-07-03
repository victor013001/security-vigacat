package com.vigacat.security.service.component.security;

import com.vigacat.security.persistence.component.ITokenPersistence;
import com.vigacat.security.persistence.component.UserPersistence;
import com.vigacat.security.persistence.dto.TokenDto;
import com.vigacat.security.persistence.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Long TOKEN_DURATION_MINUTES = 60L;
    private static final String LOG_PREFIX = "Token Service >>";

    private final ITokenPersistence tokenPersistence;
    private final UserPersistence userPersistence;

    @Override
    public String createToken(String username) {
        final String token = UUID.randomUUID().toString();
        final TokenDto tokenDto = TokenDto.builder()
                .username(username)
                .token(token)
                .expiresAt(LocalDateTime.now().plus(TOKEN_DURATION_MINUTES, ChronoUnit.MINUTES))
                .build();
        tokenPersistence.saveNewToken(tokenDto);
        return token;
    }

    @Override
    public TokenDto getValidToken(String token) {
        TokenDto tokenDto = tokenPersistence.getToken(token)
                .orElseThrow(() -> new BadCredentialsException("Invalid"));
        if(LocalDateTime.now().isBefore(tokenDto.getExpiresAt())){
            return tokenDto;
        } else {
            throw new BadCredentialsException("Expired");
        }
    }

    @Override
    public Authentication buildAuthentication(TokenDto tokenDto, Long appId) {
        final String username = tokenDto.getUsername();
        final UserDto userDto = userPersistence.getUserByUsernameAndApp(username, appId);

        log.info("{} Build Authentication for name {} and app Id {}", LOG_PREFIX, username, appId);
        return new UsernamePasswordAuthenticationToken(username, tokenDto.getToken(), createAuthorities(userDto));
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
