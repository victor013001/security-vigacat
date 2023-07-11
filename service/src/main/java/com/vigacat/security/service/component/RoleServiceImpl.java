package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.RolePersistence;
import com.vigacat.security.persistence.dto.PermissionDto;
import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.service.component.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private static final String LOG_PREFIX = "Role Service >>";

    private final RolePersistence rolePersistence;
    private final PermissionService permissionService;
    private final TokenService tokenService;

    @Override
    public RoleDto createNewRole(String roleNameDto, List<String> rolePermissionNames, String authorization, Long appId) {

        if (roleNameDto.isEmpty() || rolePermissionNames.isEmpty()) {
            throw new InvalidParameterException("All parameters are required");
        }

        if (rolePersistence.getRoleByNameAndAppId(roleNameDto, appId).isPresent()) {
            throw new DuplicateKeyException("Role already exist");
        }

        List<PermissionDto> permissionsDto = permissionService.getPermissionsByName(rolePermissionNames);

        String usernameToken = tokenService.getValidToken(authorization).getUsername();

        RoleDto roleDto = RoleDto.builder()
                .name(roleNameDto)
                .permissions(permissionsDto)
                .build();

        log.info("{} Save role with name {} and app id {}, created by {}", LOG_PREFIX, roleNameDto, appId, usernameToken);
        return rolePersistence.saveNewRole(roleDto, usernameToken, appId);
    }

    @Override
    public List<RoleDto> getRolesByIds(List<Long> roleIds) {

        log.info("{} Get roles by ids", LOG_PREFIX);

        List<RoleDto> roleDtos = rolePersistence.getRolesById(roleIds);

        if (roleIds.size() != roleDtos.size()) {
            throw new InvalidParameterException("Roles doesn't exist");
        }

        return roleDtos;
    }
}
