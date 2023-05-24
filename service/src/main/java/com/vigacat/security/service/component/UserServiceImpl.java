package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.UserPersistence;
import com.vigacat.security.persistence.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserPersistence userPersistence;

    @Override
    public UserDto getUser(String username, Long appId) {
        log.info("User Service >> Get User with name {} and app Id {}", username, appId);
        return userPersistence.getUserByUsernameAndApp(username, appId);
    }

    @Override
    public UserDto getUserWithoutFetch(String username, Long appId) {
        return userPersistence.getUser(username, appId);
    }

}
