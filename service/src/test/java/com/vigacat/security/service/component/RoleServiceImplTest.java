package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.RolePersistence;
import com.vigacat.security.persistence.dto.PermissionDto;
import com.vigacat.security.persistence.dto.RoleDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RolePersistence rolePersistence;
    @Mock
    private PermissionService permissionService;

    @Test
    public void createNewRole() {

        String roleNameUser = "User";
        String permissionCreate = "Create";
        String permissionRead = "Read";

        Long appId = 1L;

        List<String> permissionNames = List.of(
                permissionCreate,
                permissionRead
        );

        PermissionDto permissionDtoCreate = PermissionDto.builder()
                .permission("Create")
                .build();

        PermissionDto permissionDtoRead = PermissionDto.builder()
                .permission("Read")
                .build();

        List<PermissionDto> permissionDtos = List.of(
                permissionDtoCreate,
                permissionDtoRead
        );

        RoleDto roleDtoUser = RoleDto.builder()
                .name(roleNameUser)
                .permissions(permissionDtos)
                .build();

        Mockito.when(rolePersistence.roleNameExist(roleNameUser, appId))
                .thenReturn(false);

        Mockito.when(permissionService.getPermissionsByNames(permissionNames))
                .thenReturn(permissionDtos);

        Mockito.when(rolePersistence.saveNewRole(roleDtoUser, appId))
                .thenReturn(roleDtoUser);

        final RoleDto roleDtoUserResponse = roleService.createNewRole(roleNameUser, permissionNames, appId);

        Mockito.verify(rolePersistence)
                .roleNameExist(roleNameUser, appId);

        Mockito.verify(permissionService)
                .getPermissionsByNames(permissionNames);

        Assertions.assertThat(roleDtoUserResponse)
                .hasFieldOrPropertyWithValue("name", roleNameUser);

        Assertions.assertThat(roleDtoUserResponse.getPermissions())
                .extracting(PermissionDto::getPermission)
                .contains(permissionCreate, permissionRead);
    }

    @Test
    public void getRolesByIds() {

        List<Long> roleIds = List.of(1L, 2L);

        Mockito.when(rolePersistence.roleIdsExist(roleIds))
                .thenReturn(true);

        final boolean roleIdsExistResponse = roleService.roleIdsExist(roleIds);

        Mockito.verify(rolePersistence)
                .roleIdsExist(roleIds);

        Assertions.assertThat(roleIdsExistResponse)
                .isTrue();
    }

}
