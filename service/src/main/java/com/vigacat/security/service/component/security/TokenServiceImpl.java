package com.vigacat.security.service.component.security;

import com.vigacat.security.persistence.component.TokenPersistence;
import com.vigacat.security.persistence.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Long TOKEN_DURATION_MINUTES = 60L;


    private final TokenPersistence tokenPersistence;

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
    public Optional<TokenDto> getToken(String token) {
        return tokenPersistence.getToken(token);
    }

}
