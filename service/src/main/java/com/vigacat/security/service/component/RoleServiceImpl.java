package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.RolePersistence;
import com.vigacat.security.persistence.dto.PermissionDto;
import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.service.exceptions.RoleCreateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public RoleDto createNewRole(String roleName, List<String> rolePermissionNames, Long appId) {

        checkRoleNameUnique(roleName, appId);

        List<PermissionDto> rolePermissionsDto = getExistingRolePermissions(roleName, rolePermissionNames, appId);

        RoleDto roleDto = createRoleDto(roleName, rolePermissionsDto);

        log.info("{} Save role with name {} and app id {}", LOG_PREFIX, roleName, appId);

        return rolePersistence.saveNewRole(roleDto, appId);
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

    private void checkRoleNameUnique(String roleName, Long appId) {
        if (rolePersistence.roleNameExist(roleName, appId)) {
            throw new RoleCreateException("Role name already exists in the app", roleName, appId, RoleCreateException.Type.DUPLICATE_NAME);
        }
    }

    private List<PermissionDto> getExistingRolePermissions(String roleName, List<String> rolePermissionNames, Long appId) {

        List<PermissionDto> rolePermissionDtos = permissionService.getPermissionsByNames(rolePermissionNames);

        if (rolePermissionNames.size() != rolePermissionDtos.size()) {
            throw new RoleCreateException("One or more permission doesn't exist", roleName, appId, RoleCreateException.Type.NON_EXISTENT_PERMISSIONS);
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
