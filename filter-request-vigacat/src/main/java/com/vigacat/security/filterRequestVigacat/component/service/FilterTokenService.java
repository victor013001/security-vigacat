package com.vigacat.security.filterRequestVigacat.component.service;

import com.vigacat.security.filterRequestVigacat.dto.TokenDto;

public interface FilterTokenService {

    TokenDto getValidToken(String token);

}
