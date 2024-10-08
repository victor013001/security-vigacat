package com.vigacat.security.service.component;

import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UserRolesPermissionsDto;
import com.vigacat.security.persistence.dto.UserToSaveDto;
import com.vigacat.security.persistence.dto.UsernamePasswordDto;

import java.util.Optional;

public interface UserService {
    UserDto getUser(String username, Long appId);

    UserDto getUserWithoutFetch(String username, Long appId);

    Optional<UsernamePasswordDto> getUserByUsername(String username);

    UserDto createNewUser(UserToSaveDto userToSaveDto);

    UserRolesPermissionsDto getUserRolesAndPermissionsByTokenAndAppName(String authorization, String appName);

}
