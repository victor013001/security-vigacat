package com.vigacat.security.filterRequestVigacat.component.service;

import com.vigacat.security.filterRequestVigacat.component.request.TokenRequest;
import com.vigacat.security.filterRequestVigacat.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FilterTokenServiceImpl implements FilterTokenService {

    private final TokenRequest tokenRequest;

    @Override
    public TokenDto getValidToken(String token) {
        TokenDto tokenDto = tokenRequest.getToken(token)
                .orElseThrow(() -> new BadCredentialsException("Invalid"));
        if (LocalDateTime.now().isBefore(tokenDto.getExpiresAt())) {
            return tokenDto;
        } else {
            throw new BadCredentialsException("Expired");
        }
    }
}
