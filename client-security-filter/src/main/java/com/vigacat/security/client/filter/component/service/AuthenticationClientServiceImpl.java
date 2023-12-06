package com.vigacat.security.client.filter.component.service;

import com.vigacat.security.client.filter.dto.UserRolesPermissionsDto;
import com.vigacat.security.client.filter.component.webclient.UserRequestClient;
import com.vigacat.security.client.filter.dto.PermissionDto;
import com.vigacat.security.client.filter.dto.RoleDto;
import lombok.RequiredArgsConstructor;
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
public class AuthenticationClientServiceImpl implements AuthenticationClientService {

    private final UserRequestClient clientRequestUser;

    @Override
    public Authentication buildAuthentication(String authorization) {
        UserRolesPermissionsDto userRolesPermissionsDto = clientRequestUser.getUserRolesPermissionsByToken(authorization);
        return new UsernamePasswordAuthenticationToken(
                userRolesPermissionsDto.getName(),
                authorization,
                createRolesAuthorities(userRolesPermissionsDto));
    }

    private GrantedAuthority createSimpleAuthority(String prefix, String name) {
        return new SimpleGrantedAuthority(prefix.concat("::").concat(name));
    }

    private Set<GrantedAuthority> createRolesAuthorities(UserRolesPermissionsDto userRolesPermissionsDto) {
        Set<GrantedAuthority> authorities = userRolesPermissionsDto.getRoles().stream()
                .map(RoleDto::getName)
                .map(roleName -> createSimpleAuthority("role", roleName))
                .collect(Collectors.toSet());
        authorities.addAll(createPermissionsAuthorities(userRolesPermissionsDto.getPermissions()));

        return authorities;
    }

    private Set<GrantedAuthority> createPermissionsAuthorities(List<PermissionDto> permissions) {
        return permissions.stream()
                .map(PermissionDto::getPermission)
                .map(permissionName -> createSimpleAuthority("permission", permissionName))
                .collect(Collectors.toSet());
    }

}
