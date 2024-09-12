package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.RolePersistence;
import com.vigacat.security.persistence.dto.PermissionDto;
import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.service.component.security.util.VigacatSecurityContext;
import com.vigacat.security.service.exceptions.RoleCreateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private static final String LOG_PREFIX = "Role Service >>";

    private final RolePersistence rolePersistence;
    private final PermissionService permissionService;
    private final VigacatSecurityContext vigacatSecurityContext;

    @Override
    public RoleDto createNewRole(String roleName, List<String> rolePermissionNames, Long appId) {
        checkRoleNameUnique(roleName, appId);
        List<PermissionDto> rolePermissionsDto = getExistingRolePermissions(rolePermissionNames, appId);
        RoleDto roleDto = createRoleDto(roleName, rolePermissionsDto);
        String usernameAuthenticated = vigacatSecurityContext.getUsernameAuthenticated();
        log.info("{} Save role with name {} and app id {}, created by {}", LOG_PREFIX, roleName, appId, usernameAuthenticated);
        return rolePersistence.saveNewRole(roleDto, appId, usernameAuthenticated);
    }

    @Override
    public boolean roleIdsExist(List<Long> roleIds) {
        log.info("{} Check roles by ids", LOG_PREFIX);
        return rolePersistence.roleIdsExist(roleIds);
    }

    private void checkRoleNameUnique(String roleName, Long appId) {
        if (rolePersistence.roleNameExist(roleName, appId)) {
            throw new RoleCreateException(roleName, appId, RoleCreateException.Type.DUPLICATE_NAME);
        }
    }

    private List<PermissionDto> getExistingRolePermissions(List<String> rolePermissionNames, Long appId) {
        List<PermissionDto> rolePermissionDtos = permissionService.getPermissionsByNames(rolePermissionNames);
        if (rolePermissionNames.size() != rolePermissionDtos.size()) {
            throw new RoleCreateException(rolePermissionNames, appId, RoleCreateException.Type.NON_EXISTENT_PERMISSIONS);
        }
        return rolePermissionDtos;
    }

    private RoleDto createRoleDto(String roleName, List<PermissionDto> rolePermissionsDto) {
        return RoleDto.builder()
                .name(roleName)
                .permissions(rolePermissionsDto)
                .build();
    }
}
