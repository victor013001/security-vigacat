package com.vigacat.security.persistence.component;

import com.vigacat.security.persistence.dto.TokenDto;

import java.util.Optional;

public interface TokenPersistence {
    Optional<TokenDto> getToken(String token);

    void saveNewToken(TokenDto tokenDto);
}
