package com.vigacat.security.service.component;

import com.vigacat.security.persistence.dto.TokenDto;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UsernamePasswordDto;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserService {
    UserDto getUser(String username, Long appId);

    UserDto getUserWithoutFetch(String username, Long appId);

    Optional<UsernamePasswordDto> getUserByUsername(String username);

    Authentication buildAuthentication(TokenDto tokenDto, Long appId);

}
