package com.vigacat.security.client.filter.component.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationClientService {
    Authentication buildAuthentication(String authorization);
}
