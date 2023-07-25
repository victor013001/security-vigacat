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

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RolePersistenceImplTest {

    @InjectMocks
    private RolePersistenceImpl rolePersistence;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AppRepository appRepository;

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

        Mockito.when(appRepository.getReferenceById(appId))
                .thenReturn(appReference);

        Mockito.when(roleRepository.save(Mockito.any(Role.class)))
                .thenReturn(roleAdmin);

        final RoleDto adminRoleDtoResponse = rolePersistence.saveNewRole(roleAdminDto, 1L, usernameAdminAuthenticated);

        Mockito.verify(roleRepository)
                .save(Mockito.any(Role.class));

        Assertions.assertThat(adminRoleDtoResponse)
                .hasFieldOrPropertyWithValue("name", roleNameUser);

        Assertions.assertThat(adminRoleDtoResponse.getPermissions())
                .extracting(PermissionDto::getPermission)
                .contains(permissionNameRead);
    }

}
