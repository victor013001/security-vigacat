package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.UserPersistence;
import com.vigacat.security.persistence.dto.TokenDto;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UsernamePasswordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public Authentication buildAuthentication(TokenDto tokenDto, Long appId) {
        final String username = tokenDto.getUsername();
        final UserDto userDto = userPersistence.getUserByUsernameAndApp(username, appId);

        log.info("{} Build Authentication for name {} and app Id {}", LOG_PREFIX, username, appId);
        return new UsernamePasswordAuthenticationToken(username, tokenDto.getToken(), createAuthorities(userDto));
    }

    private Set<GrantedAuthority> createAuthorities(UserDto userDto) {
        return userDto.getRoles().stream()
                .flatMap(roleDto ->
                        Stream.concat(
                                Stream.of("role::".concat(roleDto.getName())),
                                roleDto.getPermissions().stream()
                                        .map(permissionDto ->
                                                "permission::".concat(permissionDto.getPermission())
                                        )
                        )
                )
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

}
