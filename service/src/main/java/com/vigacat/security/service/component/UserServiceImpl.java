package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.UserPersistence;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UsernamePasswordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private static final String LOG_PREFIX = "User Service >>";

    private final UserPersistence userPersistence;

    @Override
    public UserDto getUser(String username, Long appId) {
        log.info("{} Get User with name {} and app Id {}",LOG_PREFIX, username, appId);
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

}
