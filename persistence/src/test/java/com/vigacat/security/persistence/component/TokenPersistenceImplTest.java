package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.Token;
import com.vigacat.security.dao.repository.TokenRepository;
import com.vigacat.security.persistence.dto.TokenDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TokenPersistenceImplTest {

    @Captor
    ArgumentCaptor<String> usernameArgumentCaptor;
    @Captor
    ArgumentCaptor<Token> tokenArgumentCaptor;
    @InjectMocks
    private TokenPersistenceImpl tokenPersistence;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void getToken() {

        String token = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";
        String user = "user";

        Token userToken = Token.builder()
                .token(token)
                .username(user)
                .build();

        TokenDto userTokenDto = TokenDto.builder()
                .token(token)
                .username(user)
                .build();

        Mockito.when(tokenRepository.findByToken(token))
                .thenReturn(Optional.of(userToken));

        Mockito.when(modelMapper.map(Mockito.any(Token.class), Mockito.eq(TokenDto.class)))
                .thenReturn(userTokenDto);

        final Optional<TokenDto> tokenDtoResponse = tokenPersistence.getToken(token);

        Mockito.verify(tokenRepository)
                .findByToken(token);

        Mockito.verify(modelMapper)
                .map(Mockito.any(Token.class), Mockito.eq(TokenDto.class));

        Assertions.assertThat(tokenDtoResponse)
                .isNotEmpty()
                .contains(userTokenDto);
    }

    @Test
    public void saveNewToken() {

        String token = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";
        String user = "user";
        LocalDateTime userTokenExpiresAt = LocalDateTime.now().plus(60L, ChronoUnit.MINUTES);

        TokenDto userTokenDto = TokenDto.builder()
                .token(token)
                .username(user)
                .expiresAt(userTokenExpiresAt)
                .build();

        Token userToken = Token.builder()
                .token(token)
                .username(user)
                .expiresAt(userTokenExpiresAt)
                .build();

        Mockito.when(modelMapper.map(Mockito.any(TokenDto.class), Mockito.eq(Token.class)))
                .thenReturn(userToken);

        tokenPersistence.saveNewToken(userTokenDto);

        Mockito.verify(tokenRepository)
                .deleteById(usernameArgumentCaptor.capture());

        Mockito.verify(tokenRepository)
                .save(tokenArgumentCaptor.capture());

        Token tokenArgumentCaptorValue = tokenArgumentCaptor.getValue();
        String usernameArgumentCaptorValue = usernameArgumentCaptor.getValue();

        Assertions.assertThat(tokenArgumentCaptorValue)
                .isEqualTo(userToken);

        Assertions.assertThat(usernameArgumentCaptorValue)
                .isEqualTo(user);


    }

}
