package com.vigacat.security.persistence.component;

import com.vigacat.security.persistence.dto.UserDto;

public interface UserPersistence {
    UserDto getUserByUsernameAndApp(String username, Long appId);

    UserDto getUser(String username, Long appId);
}
