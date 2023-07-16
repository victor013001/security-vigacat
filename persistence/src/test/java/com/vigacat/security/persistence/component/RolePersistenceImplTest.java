package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.App;
import com.vigacat.security.dao.entity.Role;
import com.vigacat.security.dao.repository.AppRepository;
import com.vigacat.security.dao.repository.RoleRepository;
import com.vigacat.security.persistence.dto.PermissionDto;
import com.vigacat.security.persistence.dto.RoleDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RolePersistenceImplTest {

    @InjectMocks
    private RolePersistenceImpl rolePersistence;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AppRepository appRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @Spy
    private ModelMapper modelMapper;

    @Test
    public void roleNameExist() {

        String roleNameAdmin = "Admin";
        Long appId = 1L;

        Mockito.when(roleRepository.existsRoleByNameAndAppId(roleNameAdmin, appId))
                .thenReturn(true);

        final boolean roleNameAdminExist = rolePersistence.roleNameExist(roleNameAdmin, appId);

        Assertions.assertThat(roleNameAdminExist)
                .isTrue();
    }


    @Test
    public void saveNewRole() {

        String roleNameUser = "User";
        String usernameAdminAuthenticated = "userAdmin";
        String permissionNameRead = "Read";

        Long appId = 1L;

        PermissionDto permissionDtoRead = PermissionDto.builder()
                .permission(permissionNameRead)
                .build();

        List<PermissionDto> rolePermissionDtos = List.of(permissionDtoRead);

        RoleDto roleAdminDto = RoleDto.builder()
                .name(roleNameUser)
                .permissions(rolePermissionDtos)
                .build();

        App appReference = App.builder()
                .id(appId)
                .build();

        Role roleAdmin = modelMapper.map(roleAdminDto, Role.class);

        Mockito.when(authentication.getName())
                .thenReturn(usernameAdminAuthenticated);

        Mockito.when(securityContext.getAuthentication())
                .thenReturn(authentication);

        Mockito.when(appRepository.getReferenceById(appId))
                .thenReturn(appReference);

        Mockito.when(roleRepository.save(Mockito.any(Role.class)))
                .thenReturn(roleAdmin);

        SecurityContextHolder.setContext(securityContext);

        final RoleDto adminRoleDtoResponse = rolePersistence.saveNewRole(roleAdminDto, 1L);

        Mockito.verify(roleRepository)
                .save(Mockito.any(Role.class));

        Assertions.assertThat(adminRoleDtoResponse)
                .hasFieldOrPropertyWithValue("name", roleNameUser);

        Assertions.assertThat(adminRoleDtoResponse.getPermissions())
                .extracting(PermissionDto::getPermission)
                .contains(permissionNameRead);
    }

    @Test
    public void getRolesById() {

        List<Long> roleIds = List.of(1L, 2L);

        List<Role> roles = List.of(
                Role.builder()
                        .id(1L)
                        .build(),
                Role.builder()
                        .id(2L)
                        .build()
        );

        List<RoleDto> roleDtos = List.of(
                RoleDto.builder()
                        .id(1L)
                        .build(),
                RoleDto.builder()
                        .id(2L)
                        .build()
        );


        Mockito.when(roleRepository.findRolesByIdIn(roleIds))
                .thenReturn(roles);

        Mockito.when(modelMapper.map(Mockito.any(Role.class), Mockito.eq(RoleDto.class)))
                .thenReturn(roleDtos.get(0), roleDtos.get(1));

        final List<RoleDto> roleDtosResponse = rolePersistence.getRolesById(roleIds);

        Mockito.verify(roleRepository)
                .findRolesByIdIn(roleIds);

        Mockito.verify(modelMapper, Mockito.times(2))
                .map(Mockito.any(Role.class), Mockito.eq(RoleDto.class));

        Assertions.assertThat(roleDtosResponse)
                .extracting(RoleDto::getId)
                .contains(1L, 2L);
    }

}
