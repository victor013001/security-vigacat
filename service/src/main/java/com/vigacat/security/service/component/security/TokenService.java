package com.vigacat.security.service.component.security;

import com.vigacat.security.persistence.dto.TokenDto;

public interface TokenService {
    String createToken(String username);

    TokenDto getValidToken(String token);

}
