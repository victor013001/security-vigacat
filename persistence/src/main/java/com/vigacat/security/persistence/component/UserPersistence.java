package com.vigacat.security.persistence.component;

import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UserToSaveDto;
import com.vigacat.security.persistence.dto.UsernamePasswordDto;

import java.util.Optional;

public interface UserPersistence {
    UserDto getUserByUsernameAndApp(String username, Long appId);

    UserDto getUser(String username, Long appId);

    Optional<UsernamePasswordDto> getUserByUsername(String username);

    boolean userNameOrEmailExist(String username, String email);

    UserDto saveNewUser(UserToSaveDto userToSaveDto);
}
