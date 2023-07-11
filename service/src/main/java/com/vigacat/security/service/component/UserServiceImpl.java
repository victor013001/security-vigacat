package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.UserPersistence;
import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UserToSaveDto;
import com.vigacat.security.persistence.dto.UsernamePasswordDto;
import com.vigacat.security.service.component.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
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
    private final TokenService tokenService;

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
    public UserDto createNewUser(UserToSaveDto userDto, String authorization) {

        String username = userDto.getName();
        String userEmail = userDto.getEmail();
        String userPassword = userDto.getPassword();
        List<Long> roleIds = userDto.getRoles().stream()
                .map(RoleDto::getId)
                .collect(Collectors.toList());

        if (username.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || roleIds.isEmpty()) {
            throw new InvalidParameterException("All parameters are required");
        }

        if (!userPersistence.getUsersByNameOrEmail(username, userEmail).isEmpty()) {
            throw new DuplicateKeyException("Name or email already exist");
        }

        roleService.getRolesByIds(roleIds);

        userDto.setPassword(passwordEncoder.encode(userPassword));

        String usernameToken = tokenService.getValidToken(authorization).getUsername();

        log.info("{} Save user with name {} and email {}, created by {}", LOG_PREFIX, username, userEmail, usernameToken);
        return userPersistence.saveNewUser(userDto, usernameToken);
    }

}
