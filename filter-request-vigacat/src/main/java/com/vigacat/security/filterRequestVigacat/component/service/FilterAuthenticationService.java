package com.vigacat.security.filterRequestVigacat.component.service;

import com.vigacat.security.filterRequestVigacat.dto.TokenDto;
import org.springframework.security.core.Authentication;

public interface FilterAuthenticationService {

    Authentication buildAuthentication(TokenDto tokenDto, Long appId);
}
