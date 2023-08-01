package com.vigacat.security.service.component.security;

import com.vigacat.security.persistence.dto.TokenDto;

import java.util.Optional;

public interface TokenService {
    String createToken(String username);

    Optional<TokenDto> getToken(String token);
}
