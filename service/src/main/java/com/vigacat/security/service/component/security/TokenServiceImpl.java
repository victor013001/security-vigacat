package com.vigacat.security.service.component.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService{

    @Override
    public String createToken(String username) {
        return UUID.randomUUID().toString();
    }

}
