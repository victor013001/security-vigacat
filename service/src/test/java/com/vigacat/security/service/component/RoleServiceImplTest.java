package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.RolePersistence;
import com.vigacat.security.persistence.dto.PermissionDto;
import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.persistence.dto.TokenDto;
import com.vigacat.security.service.component.security.TokenService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RolePersistence rolePersistence;
    @Mock
    private PermissionService permissionService;
    @Mock
    private TokenService tokenService;

//    @Test
//    public void createNewRole() {
//
//        String roleNameUser = "User";
//        String usernameAdmin = "Admin";
//        String tokenAdmin = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";
//
//        Long appId = 1L;
//
//        List<String> permissionNames = List.of(
//                "Create",
//                "Read"
//        );
//
//        PermissionDto permissionDtoCreate = PermissionDto.builder()
//                .permission("Create")
//                .build();
//
//        PermissionDto permissionDtoRead = PermissionDto.builder()
//                .permission("Read")
//                .build();
//
//        List<PermissionDto> permissionDtos = List.of(
//                permissionDtoCreate,
//                permissionDtoRead
//        );
//
//        TokenDto tokenUserAdminDto = TokenDto.builder()
//                .username(usernameAdmin)
//                .token(tokenAdmin)
//                .build();
//
//        RoleDto roleDtoUser = RoleDto.builder()
//                .name(roleNameUser)
//                .permissions(permissionDtos)
//                .build();
//
//        Mockito.when(rolePersistence.getRoleByNameAndAppId(roleNameUser, appId))
//                .thenReturn(Optional.empty());
//
//        Mockito.when(permissionService.getPermissionsByName(permissionNames))
//                .thenReturn(permissionDtos);
//
//        Mockito.when(tokenService.getValidToken(tokenAdmin))
//                .thenReturn(tokenUserAdminDto);
//
//        Mockito.when(rolePersistence.saveNewRole(roleDtoUser, usernameAdmin, appId))
//                .thenReturn(roleDtoUser);
//
//        final RoleDto roleDtoUserResponse = roleService.createNewRole(roleNameUser, permissionNames, appId);
//
//        Mockito.verify(rolePersistence)
//                .getRoleByNameAndAppId(roleNameUser, appId);
//
//        Mockito.verify(permissionService)
//                .getPermissionsByName(permissionNames);
//
//        Mockito.verify(tokenService)
//                .getValidToken(tokenAdmin);
//
//        Assertions.assertThat(roleDtoUserResponse)
//                .isEqualTo(roleDtoUser);
//    }
}
