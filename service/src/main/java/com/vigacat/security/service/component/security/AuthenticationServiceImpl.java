package com.vigacat.security.service.component.security;

import com.vigacat.security.persistence.component.PermissionPersistence;
import com.vigacat.security.persistence.component.UserPersistence;
import com.vigacat.security.persistence.dto.PermissionDto;
import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.persistence.dto.TokenDto;
import com.vigacat.security.persistence.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String LOG_PREFIX = "Authentication Service >>";
    private final UserPersistence userPersistence;
    private final PermissionPersistence permissionPersistence;

    @Override
    public Authentication buildAuthentication(TokenDto tokenDto, Long appId) {
        final String username = tokenDto.getUsername();
        final UserDto userDto = userPersistence.getUserByUsernameAndApp(username, appId);
        log.info("{} Build Authentication for name {} and app Id {}", LOG_PREFIX, username, appId);
        return new UsernamePasswordAuthenticationToken(username, tokenDto.getToken(), createRolesAuthorities(userDto.getRoles()));
    }

    private Set<GrantedAuthority> createRolesAuthorities(List<RoleDto> roleDtoList) {

        Set<GrantedAuthority> authorities = roleDtoList.stream()
                .map(RoleDto::getName)
                .map(roleName -> createSimpleAuthority("role", roleName))
                .collect(Collectors.toSet());

        List<Long> roleIds = roleDtoList.stream()
                .map(RoleDto::getId)
                .collect(Collectors.toList());

        authorities.addAll(createPermissionsAuthorities(roleIds));

        return authorities;
    }

    private GrantedAuthority createSimpleAuthority(String prefix, String name) {
        return new SimpleGrantedAuthority(prefix.concat("::").concat(name));
    }

    private Set<GrantedAuthority> createPermissionsAuthorities(List<Long> roleIds) {
        return permissionPersistence.getPermissionsByRoleIds(roleIds).stream()
                .map(PermissionDto::getPermission)
                .map(permissionName -> createSimpleAuthority("permission", permissionName))
                .collect(Collectors.toSet());
    }
}