package com.vigacat.security.service.component.security;

import com.vigacat.security.persistence.dto.TokenDto;
import org.springframework.security.core.Authentication;

public interface TokenService {
    String createToken(String username);

    TokenDto getValidToken(String token);

    Authentication buildAuthentication(TokenDto tokenDto, Long appId);

}
