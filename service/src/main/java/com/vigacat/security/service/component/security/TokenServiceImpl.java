package com.vigacat.security.service.component.security;

import com.vigacat.security.persistence.component.ITokenPersistence;
import com.vigacat.security.persistence.dto.TokenDto;
import com.vigacat.security.service.component.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Long TOKEN_DURATION_MINUTES = 60L;

    private final ITokenPersistence tokenPersistence;
    private final UserService userService;

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

}
