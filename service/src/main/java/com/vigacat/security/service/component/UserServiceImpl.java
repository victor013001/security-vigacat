package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.UserPersistence;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UserToSaveDto;
import com.vigacat.security.persistence.dto.UsernamePasswordDto;
import com.vigacat.security.service.component.security.util.VigacatSecurityContext;
import com.vigacat.security.service.exceptions.UserCreateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String LOG_PREFIX = "User Service >>";

    private final UserPersistence userPersistence;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final VigacatSecurityContext vigacatSecurityContext;

    @Override
    public UserDto getUser(String username, Long appId) {
        log.info("{} Get User with name {} and app Id {}", LOG_PREFIX, username, appId);
        return userPersistence.getUserByUsernameAndApp(username, appId);
    }

    @Override
    public UserDto getUserWithoutFetch(String username, Long appId) {
        return userPersistence.getUser(username, appId);
    }

    @Override
    public Optional<UsernamePasswordDto> getUserByUsername(String username) {
        return userPersistence.getUserByUsername(username);
    }

    @Override
    public UserDto createNewUser(UserToSaveDto userToSaveDto) {
        String username = userToSaveDto.getName();
        String userEmail = userToSaveDto.getEmail();
        List<Long> roleIds = userToSaveDto.getRoleIds();
        checkNameAndEmailUnique(username, userEmail);
        checkExistingRoles(roleIds);
        encodePassword(userToSaveDto);
        String usernameAuthenticated = vigacatSecurityContext.getUsernameAuthenticated();
        log.info("{} Save user with name {}, email {} and roleIds {}, created by {}", LOG_PREFIX, username, userEmail, roleIds, usernameAuthenticated);
        return userPersistence.saveNewUser(userToSaveDto, usernameAuthenticated);
    }

    private void checkNameAndEmailUnique(String username, String userEmail) {
        if (userPersistence.userNameOrEmailExist(username, userEmail)) {
            throw new UserCreateException(username, userEmail, UserCreateException.Type.DUPLICATE_NAME_EMAIL);
        }
    }

    private void checkExistingRoles(List<Long> roleIds) {
        if (!roleService.roleIdsExist(roleIds)) {
            throw new UserCreateException(roleIds, UserCreateException.Type.NON_EXISTING_ROLES);
        }
    }

    private void encodePassword(UserToSaveDto userToSaveDto) {
        userToSaveDto.setPassword(passwordEncoder.encode(userToSaveDto.getPassword()));
    }

}
