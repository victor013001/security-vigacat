package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.Token;
import com.vigacat.security.dao.repository.TokenRepository;
import com.vigacat.security.persistence.dto.TokenDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class TokenPersistence implements ITokenPersistence{

    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TokenPersistence(TokenRepository tokenRepository, ModelMapper modelMapper) {
        this.tokenRepository = tokenRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TokenDto> getToken(String token) {
        return tokenRepository.findByToken(token)
                .map(tokenEntity -> modelMapper.map(tokenEntity, TokenDto.class));
    }

    @Override
    @Transactional
    public void saveNewToken(TokenDto tokenDto) {
        tokenRepository.deleteById(tokenDto.getUsername());
        final Token token = modelMapper.map(tokenDto, Token.class);
        tokenRepository.save(token);
    }

}
