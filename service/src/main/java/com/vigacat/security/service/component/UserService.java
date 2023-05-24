package com.vigacat.security.service.component;

import com.vigacat.security.persistence.dto.UserDto;

public interface UserService {
    UserDto getUser(String username, Long appId);
}
