package com.vigacat.security.filterRequestVigacat.component.request;

import com.vigacat.security.filterRequestVigacat.dto.TokenDto;

import java.util.Optional;

public interface TokenRequest {
    Optional<TokenDto> getToken(String token);
}
