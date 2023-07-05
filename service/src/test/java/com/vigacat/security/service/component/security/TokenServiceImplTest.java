package com.vigacat.security.service.component.security;

import com.vigacat.security.persistence.component.TokenPersistence;
import com.vigacat.security.persistence.component.UserPersistenceImpl;
import com.vigacat.security.persistence.dto.TokenDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceImplTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private TokenPersistence tokenPersistence;


    @Test
    public void getValidToken() {
        String usernameVictor = "victor";
        String tokenVictor = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";
        LocalDateTime tokenVictorExpiresAt = LocalDateTime.now().plus(60L, ChronoUnit.MINUTES);

        final TokenDto tokenDtoVictor = TokenDto.builder()
                .username(usernameVictor)
                .token(tokenVictor)
                .expiresAt(tokenVictorExpiresAt)
                .build();

        Mockito.when(tokenPersistence.getToken(tokenVictor))
                .thenReturn(Optional.of(tokenDtoVictor));

        final TokenDto tokenDtoResponse = tokenService.getValidToken(tokenVictor);

        Mockito.verify(tokenPersistence)
                .getToken(tokenVictor);

        Assertions.assertThat(tokenDtoResponse)
                .hasFieldOrPropertyWithValue("username", usernameVictor)
                .hasFieldOrPropertyWithValue("token", tokenVictor)
                .hasFieldOrPropertyWithValue("expiresAt", tokenVictorExpiresAt);

    }

}
