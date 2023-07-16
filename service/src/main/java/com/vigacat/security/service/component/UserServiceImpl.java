package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.UserPersistence;
import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UserToSaveDto;
import com.vigacat.security.persistence.dto.UsernamePasswordDto;
import com.vigacat.security.service.exceptions.UserCreateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String LOG_PREFIX = "User Service >>";

    private final UserPersistence userPersistence;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

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

        checkNameAndEmailUnique(username,userEmail);
        checkExistingRoles(userToSaveDto.getRoles(), username,userEmail);
        encodePassword(userToSaveDto);

        log.info("{} Save user with name {} and email {}", LOG_PREFIX, username, userEmail);
        return userPersistence.saveNewUser(userToSaveDto);
    }

    private void checkNameAndEmailUnique(String username, String userEmail) {
        if (userPersistence.userNameOrEmailExist(username,userEmail)) {
            throw new UserCreateException("Name or email already exist",username,userEmail, UserCreateException.Type.DUPLICATE_NAME_EMAIL);
        }
    }
    
    private void checkExistingRoles(List<RoleDto> roleDtos, String username, String userEmail) {
        List<Long> roleIds = roleDtos.stream()
                .map(RoleDto::getId)
                .collect(Collectors.toList());

        if (!roleService.roleIdsExist(roleIds)) {
            throw new UserCreateException("One or more roles doesn't exist",username,userEmail, UserCreateException.Type.NON_EXISTING_ROLES);
        }
    }

    private void encodePassword(UserToSaveDto userToSaveDto) {
        userToSaveDto.setPassword(passwordEncoder.encode(userToSaveDto.getPassword()));
    }

}
